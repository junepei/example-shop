# 쇼핑몰 예제
# 작업 방향
상품을 세트로 묶어서 파는것을 하나의 전시관으로 생각하여 전시컨테츠화 하였습니다.
상품과 브랜드에 영향을 끼치지 않게 StyleSetProduct, StyleSetBrand라는 별개의 도메인을 만들었습니다.

# 주요 테이블 
## StyleSetProduct
상품의 속성중 set판매를 위한 정보만 모아둔 테이블. 상품번호를 PK로 사용하며 테이블 관리 정책에 따라 FK로 설정. 
|productNo | styleSetType    | styleSetPriceTag | brandNo    | brandName | price |
| :-----: | :-----: | :---: | :---: | :---: | :---: |
|상품번호(PK) | 스타일세트 구분(카테고리)   | 특성 구분  | 브랜드 번호   | 브랜드 이름  | 가격      |
### StyleSet테이블을 별도로 분리한 이유
- **상품 도메인의 영향을 최소화 시키고 싶었습니다.** <br/>
상품을 set단위로 추천하여 구매를 유도하는 것을 별도의 전시수단, 판매수단으로 생각했습니다. set로 판매하는 방식은 언제든지 중단할 수 있다고 생각했기 때문에 핵심 도메인인 product에 영향을 최소화하는 설계를 했습니다.
- **상품 테이블의 부하 최소화** <br/>
상품 테이블은 가장 많이 사용되는 테이블이고 가장 많은 정보를 저장하는 테이블입니다. set처럼 부가되는 정보 상품과 분리해서 저장하여 상품 테이블의 조회 빈도를 줄이고 상품테이블의 데이터도 늘어나지 않게 하고 싶었습니다.
- **조회시점의 부화 최소화** <br/>
운영 방법에 따라 달르겠지만 자사 상품을 파는 것이 주력인 쇼필몰은 상품의 조회가 등록/변경보다 더 부하가 클거라고 판단했습니다. 조회시점에 상품 테이블과 브랜드 테이블을 뒤져서 집계를 하면 조회에 부하가 클 수 밖에 없습니다. 상품등록시점에 필요한 집계데이터를 테이블에 추가하여 조회시점에 집계 행위를 하지 않도록 했습니다.

### styleSetType을 별도로 분리한 이유
- **카테고리는 계층 구조를 띄고 있습니다.** <br/>
set상품의 기준으로 세울 카테고리를 어떤 카테고리에 매핑 시킬 것인가가 문제가 될 수 있습니다.
상의에 추천되어 노출되어야 할 상품의 카테고리는 니트, 니트 > 집업, 니트 > 조끼 등 다양한 카테고리와 매핑이 되어야 하기 때문에 정책에 복잡도가 올라갈 수 있다고 판단했습니다.
카테고리외의 다른 분류를 추가하여 정책을 단순화 하고 운영자가 어드민을 통해서 보다 명확하게 관리할 수 있을 거라 판단 했습니다.
- **상품은 복수의 카테고리를 갖고 있습니다.** <br/>
복수의 카테고리를 이용하여 추천 영역의 어떤 카테고리를 결정하는 것은 복잡한 작업이 될 수 있습니다. 상품의 카테고리가 어떻게 등록되어 있는지에 따라 set를 운영하는 운영자의 의도와는 다른 형태로 노출이 될 수도 있습니다. 

### StyleSetProduct의 생애주기
StyleSetProduct는 Product와 일반적으로 생애주기를 함께 합니다. Product의 종속적입니다. Product가 판매가 일시중지되거나 판매종료가 된다면 StyleSetProduct에도 반영이 필요할 수 있습니다.(styleSetPriceTag 부분) 다만 변경시점은 Product의 변경없이도 StyleSetProduct의 변경이 발생할 수 있습니다. set판매의 기능확대로 set판매에 대해 부가 정보가 추가된다면 어드민을 통해 StyleSetProduct만 변경하는 경우도 있을 겁니다.

### styleSetPriceTag의 의도
상품의 노출은 사업의 의사결정에 대응할 수 있게 유연한 구조를 가져야 합니다. StyleSetProduct에 tag속성을 두어 상품 노출에 조건을 추가하거나 정렬의 우선순위를 부여할 수 있도록 설계했습니다. styleSetPriceTag는 가격과 관련된 노출을 설정할 수 있는 tag입니다.

### styleSetPriceTag의 관리
상품의 노출조건은 판매여부와 전시여부, 판매기간, 일시중지 등 여러 조건이 붙을 수 있습니다. 예제에서의 styleSetPriceTag 설정은 상품 추가/변경/삭제 상황에서만 발생하지만 판매기간, 종료기간처럼 이벤트 발생없이 시간의 경과로 상품이 노출상태가 변경이 될 수 있습니다. 실제 서비스가 진행이 된다면 데몬을 이용하여 styleSetPriceTag의 값 설정을 지속적으로 확인하여 업데이트 하는 작업이 필요하다고 계획하고 있습니다. 
## StyleSetBrand
|brandNo | brandName    | totalPrice |
| :--: | :--: | :---: |
| 브랜드 번호   | 브랜드 이름  | 가격   |

세트별 최저가 브랜드를 구하기 위해 생성하였습니다. 브랜드별 최저가를 집계하는 집계테이블 입니다.

# Response / Error 처리
### Response 처리
성공했을때와 실패했을 때 응답값을 공통화하려고 했습니다.
```json
  {
    "data": {
        "categoryName": "상의",
        "lowestPriceProduct": {
            "styleSetType": "TOP",
            "styleSetName": "상의",
            "brandNo": 3,
            "brandName": "C",
            "productNo": 27,
            "price": 10000.00
        },
        "highestPriceProduct": {
            "styleSetType": "TOP",
            "styleSetName": "상의",
            "brandNo": 9,
            "brandName": "I",
            "productNo": 75,
            "price": 11400.00
        }
    },
    "success": true,
    "code": "SUCCESS",
    "message": null
}
```
data에는 내려주려는 정보. success는 성공 여부. code에는 프론트에서 분기처리를 할 수 있는 구분값을 message에는 에러의 구체적인 메세지를 담는 의도 입니다.
### Error 처리
BadRequestException과 InternalServerErrorException라는 커스텀 Exception클래스를 두어 오류를 구분해서 내려줄수 있도록 설정하였습니다. </br>
validator 기본 라이브러리를 사용하여 간단한 파라미터 체크를 하였고 MethodArgumentNotValidException에 대한 처리를 추가했습니다.</br>
JpaShopExceptionHandler을 두어 BadRequestException, InternalServerErrorException, MethodArgumentNotValidException, Exception를 분기해서 처리하도록 했습니다.


# 서버 구동 및 API
서버 구동은 프로젝트를 gradle 프로젝트로 받으셔서 구동하시면 됩니다. 개발환경은 java 11와 그래들 8.3에서 작업했습니다.</br>
H2 DB를 이용하여 구성하였고 POST맨을 통해서 호출하였습니다.  </br>
https://app.getpostman.com/join-team?invite_code=815ab5356f1dd6edba43b5a8b5ac83f7&target_code=26cc45a24cbbf8f520428357f608ecd0 </br>
위의 링크를 접속하면 호출 정보를 확인할 수 있습니다. 위의 링크가 되지 않을 것을 대비하여 각 api에 curl을 남겨둡니다.
### 구현1 카테고리별 최저가
curl --location 'localhost:8080/style-set/collections/lowest-price'
### 구현2 브랜드별 카테고리 최저가
curl --location 'localhost:8080/style-set/collections/brands/lowest-price'
### 구현3 카테고리의 최저가와 최고가 상품
curl --location 'localhost:8080/style-set/collections/categories/lowest-price?name=%EC%83%81%EC%9D%98'</br>
name : 예제의 카테고리 명
### 구현4 브랜드/상품 추가/수정/삭제
브랜드, 상품이 추가 될때 event를 발생시켜 작업했습니다. styleset 도메인에서 eventlistener로 세트정보 처리를 하는 형태로 작업했습니다. </br></br>
브랜드 추가 </br>
curl --location 'localhost:8080/brands/' \
--header 'Content-Type: application/json' \
--data '{
    "brandName" : "test13"
}'
</br></br>
브랜드 수정 </br>
curl --location --request PUT 'localhost:8080/brands/84' \
--header 'Content-Type: application/json' \
--data '{
    "brandName" : "tt"
}' </br>
/brands/84에서 84는 brandNo입니다. 브랜드 추가API의 response값인 brandNo를 84 자리에 입력하셔서 호출하시면 됩니다. </br></br>
브랜드 삭제</br>
curl --location --request DELETE 'localhost:8080/brands/84'  </br>
/brands/84에서 84는 brandNo입니다. 브랜드 추가API의 response값인 brandNo를 84 자리에 입력하셔서 호출하시면 됩니다. </br> </br>

상품 추가 </br>
curl --location 'localhost:8080/products' \
--header 'Content-Type: application/json' \
--data '{
    "categoryNo" : 10,
    "brandNo" : 1,
    "price" : 7900,
    "styleSetType" : "TOP"
}'
</br></br>
상품 수정 </br>
curl --location --request PUT 'localhost:8080/products/83' \
--header 'Content-Type: application/json' \
--data '{
    "price" : 12000,
    "styleSetType" : "TOP"
}' </br>
products/83에서 83은 productNo입니다. 상품 추가API의 response값인 productNo를 84 자리에 입력하셔서 호출하시면 됩니다. </br></br>
상품 삭제</br>
curl --location --request DELETE 'localhost:8080/products/83' </br>
products/83에서 83은 productNo입니다. 상품 추가API의 response값인 productNo를 84 자리에 입력하셔서 호출하시면 됩니다. </br></br>

# 테스트 시나리오
### 1.SampleSettingTest.setting() 실행
예제의 브랜드 A부터 I까지 브랜드와 브랜드에 속한 상품정보, 관련된 StyleSetProduct, StyleSetBrand가 DB에 저장이 됩니다.
### 2. 구현1, 구현2, 구현3 예제 확인
### 3. 브랜드 추가 확인
### 4. 브랜드 수정 확인. 3의 브랜드 추가의 response에 포함된 brandNo 이용
### 5. 브랜드 수정 확인. 3의 브랜드 추가의 response에 포함된 brandNo 이용
### 6. 상품 추가 확인. 상의-TOP 카테고리로 최저가를 설정하여 상품 추가.
### 7. 구현3을 실행하여 6번 API가 제대로 수행이 됐는지 확인. (최저가 상품에 6에서 추가한 상품이 노출.)
### 8. 상품 수정 확인. 6의 상품 추가의 response에 포함된 productNo이용. 6에서 최저가로 설정된 상품을 최고가로 설정하여 수정.
### 9. 구현3을 실행하여 8의 수행값 확인. (최저가에는 6을 실행하기 이전 최저가 상품이 노출되고 최고가에는 8에서 수정한 상품이 노출.)
### 10. 상품 삭제 확인. 6의 상품 추가의 response에 포함된 productNo이용.
### 11. 구현3을 실행하여 10의 결과값 확인. (최고가에 8에서 수정한 상품이 노출되지 않고 이전에 노출된 최고가 상품이 노출.)


