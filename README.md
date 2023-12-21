## CRUD処理すべてを備えたREST APIの作成

### 概要

消耗品の在庫一覧についてAPIを作成しました。  
今回は試験研究などで使用される試薬を題材にして在庫一覧表を作成しました。

##

プロジェクトの進捗  
(作業を行いながら作成中)  
https://github.com/users/sugao-2211/projects/1

##

#### APIの内容

- Read処理
    - 全件検索及び部分一致検索(クエリパラメータ)の実装
    - id検索(パスパラメータ検索)の実装
    - 1000以上の数字を3桁区切りで表示(1,000)する変換処理を実装
    - 部分一致検索(クエリパラメータ)の例外処理(NotFoundException)
    - id検索(パスパラメータ検索)の例外処理(NotFoundException)
- Create処理
    - データ登録処理の実装
    - データ登録における例外処理の確認１ (MethodArgumentNotValidException)
    - データ登録における例外処理の確認２ (MethodArgumentNotValidException)
    - データ登録における例外処理の確認３ (HttpMessageNotReadableException)

- Update処理
    - データ更新処理の実装
    - データ更新における例外処理の確認１ (NotFoundException)
    - データ更新における例外処理の確認２ (MethodArgumentNotValidException)
    - データ更新における例外処理の確認３ (MethodArgumentNotValidException)
    - データ更新における例外処理の確認４ (HttpMessageNotReadableException)

- Delete処理
    - データ削除処理の実装
    - データ削除における例外処理の確認 (NotFoundException)

##

#### データベース作成時の内容

|**id**|**name**|**grade**|**quantity**|**unit**|**purchase**|      
|:--:|:--:|:--:|:--:|:--:|:--:|  
|1|メタノール|HPLC用|3|L|2023−05−24|  
|2|塩化カリウム|特級|500|g|2023−07−19|  
|3|硫酸ナトリウム|特級|5|kg|2022-08-30|  
|4|グルコアミラーぜ|生化学用|10000|unit|2023-10-11|  
|5|硫酸|硫酸呈色用|500|ｍL|2023-04-05|  
|6|ピリドキシン塩酸塩|日本薬局方標準品|200|mg|2023-09-22|

***

### Read処理の実装

<details>
<summary>Read処理</summary>

以下の処理を実行

- データベース全件検索
- クエリパラメータ(name)に合致するものを部分一致検索
    - 「硫酸」で検索
- クエリパラメータ(name)の部分一致検索における例外処理
    - 「硝酸」で検索し例外処理を発生
- パスパラメータ(id)に合致するものを検索
    - id「4」で検索
- パスパラメータ(id)の検索における例外処理
    - id「9」で検索し例外処理を発生
- findById()メソッド(パスパラメータ検索)のService単体テスト
    - 存在する在庫のidをパスパラメータに指定したときに正常に在庫の情報が返されること
    - 存在しないIDをパスパラメータに指定したときにNotFoundExceptionが返されること
- findData()メソッド(クエリパラメータ検索)のService単体テスト
    - クエリパラメータを指定しなかったときにfindAllメソッドが呼び出されること
    - 存在する名前をクエリパラメータに指定したときにfindByNameメソッドが呼び出されること
    - 存在しない名前をクエリパラメータに指定したときにNotFoundExceptionが返されること

##

<details>
<summary>全件検索</summary>

- 全件検索
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList'
      ```
    - 実行結果
      <img width="1012" alt="スクリーンショット 2023-10-31 13 33 02" src="https://github.com/sugao-2211/stockListProject/assets/141313076/028da7e0-c77a-4c33-acfc-0e05c0a5d1bd">
      <img width="1010" alt="スクリーンショット 2023-10-31 13 33 23" src="https://github.com/sugao-2211/stockListProject/assets/141313076/a0430fdd-3ffd-4595-8ce0-2f9176e4b1a2">

</details>

##

<details>
<summary>部分一致検索(クエリパラメータ検索)</summary>

- 部分一致検索(クエリパラメータ検索)
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList?name=%E7%A1%AB%E9%85%B8'
      ```
      硫酸のエンコード：%E7%A1%AB%E9%85%B8
    - 実行結果
      <img width="1014" alt="スクリーンショット 2023-10-31 13 36 14" src="https://github.com/sugao-2211/stockListProject/assets/141313076/8431d332-9193-43a1-ba17-0da8de9caaa8">

##

- 部分一致検索(クエリパラメータ検索)で存在しない名前を指定した場合
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList?name=%E7%A1%9D%E9%85%B8'
      ```
      硝酸のエンコード：%E7%A1%9D%E9%85%B8
    - 実行結果
      <img width="1009" alt="スクリーンショット 2023-12-16 23 49 10" src="https://github.com/sugao-2211/stockListProject/assets/141313076/6ddc11dd-b143-4b03-849f-488f2930bf4d">

</details>

##

<details>
<summary>id検索(パスパラメータ検索)及び例外処理</summary>

- id検索(パスパラメータ検索)
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList/4'
      ```
    - 実行結果  
      <img width="1020" alt="スクリーンショット 2023-10-31 14 14 18" src="https://github.com/sugao-2211/stockListProject/assets/141313076/74df72a1-4d08-4210-b0de-8ceb9c2e8a3d">

##

- id検索(パスパラメータ検索)の例外処理
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList/9'
      ```
    - 実行結果
      <img width="1004" alt="スクリーンショット 2023-10-31 16 17 58" src="https://github.com/sugao-2211/stockListProject/assets/141313076/119ed0ee-64ee-447a-befc-c185ded3dc94">

</details>

##

<details>
<summary>findById()メソッド(パスパラメータ検索)のService単体テスト</summary>

- findById()メソッド(パスパラメータ検索)のService単体テスト
    - 存在する在庫のidをパスパラメータに指定したときに正常に在庫の情報が返されること
    - 存在しないIDをパスパラメータに指定したときにNotFoundExceptionが返されること

  https://github.com/sugao-2211/stockListProject/blob/e8d7c8dd4d7a8342de67f6051d5ed96f452e8fd8/src/test/java/com/stock/stocklist/service/StockListServiceTest.java#L1-L59

- 実行結果
  <img width="1426" alt="スクリーンショット 2023-12-04 17 55 18" src="https://github.com/sugao-2211/stockListProject/assets/141313076/7714bc6c-6570-4908-9aca-1a2ae50341d8">

</details>

##

<details>
<summary>findData()メソッド(クエリパラメータ検索)のService単体テスト</summary>

- findData()メソッド(クエリパラメータ検索)のService単体テスト
    - 在庫の名前を指定しなかったときにfindAllメソッド呼び出されて全件の在庫情報が返却されること
    - 存在する名前を指定したときにfindByNameメソッドが呼び出されて該当する在庫情報が返却されること
    - 存在しない名前を指定したときに空のListが返されること

  https://github.com/sugao-2211/stockListProject/blob/dac5df8cb3816a8ec91aad88ea8d47a3b48f2b52/src/test/java/com/stock/stocklist/service/StockListServiceTest.java#L52-L91

- 実行結果
    - 在庫の名前を指定しなかったときにfindAllメソッド呼び出されて全件の在庫情報が返却されること
      <img width="1373" alt="スクリーンショット 2023-12-13 10 56 07" src="https://github.com/sugao-2211/stockListProject/assets/141313076/d63495d2-e7ed-46fe-be2b-5bd4a214aa6b">
    - 存在する名前を指定したときにfindByNameメソッドが呼び出されて該当する在庫情報が返却されること
      <img width="1380" alt="スクリーンショット 2023-12-13 10 56 29" src="https://github.com/sugao-2211/stockListProject/assets/141313076/a394563c-2254-4620-9f19-8fe864001f6b">
    - 存在しない名前を指定したときに空のListが返されること
      <img width="1362" alt="スクリーンショット 2023-12-14 15 39 45" src="https://github.com/sugao-2211/stockListProject/assets/141313076/e893ad2f-ff4e-4909-be49-2f5f541381d2">

</details>

##

<details>
<summary>findAll()メソッドのDB単体テスト</summary>

- findAll()メソッドのDB単体テスト
    - findAll()メソッドによって全件の在庫情報が取得できること

  https://github.com/sugao-2211/stockListProject/blob/a3fc05a918a62fcfa01414b7aa416d632f9bc833/src/test/java/com/stock/stocklist/mapper/StockListMapperTest.java#L1-L42
  https://github.com/sugao-2211/stockListProject/blob/38da884c0443afb05d3d58d154dc0cdb322ab945/src/test/resources/datasets/stockList.yml#L1-L37
  https://github.com/sugao-2211/stockListProject/blob/38da884c0443afb05d3d58d154dc0cdb322ab945/src/test/resources/dbunit.yml#L1-L9
  https://github.com/sugao-2211/stockListProject/blob/38da884c0443afb05d3d58d154dc0cdb322ab945/build.gradle#L18-L28

- 実行結果
  <img width="1384" alt="スクリーンショット 2023-12-16 23 42 21" src="https://github.com/sugao-2211/stockListProject/assets/141313076/ad64405b-a2d0-4409-90d2-863baeb8bc02">

</details>

##

<details>
<summary>findByName()メソッドのDB単体テスト</summary>

- findByName()メソッドのDB単体テスト
    - 在庫名を指定したときに該当するの在庫情報が取得できること
    - 存在しない在庫名を指定したときに空のリストが返されること

  https://github.com/sugao-2211/stockListProject/blob/4a1c4063201ef38414f2568a0cb9ebcdc9b49825/src/test/java/com/stock/stocklist/mapper/StockListMapperTest.java#L42-L58

- 実行結果
    - 在庫名を指定したときに該当するの在庫情報が取得できること
      <img width="1391" alt="スクリーンショット 2023-12-17 16 19 03" src="https://github.com/sugao-2211/stockListProject/assets/141313076/89714d1e-e855-42f9-9c0d-0e2ab244e634">
    - 存在しない在庫名を指定したときに空のリストが返されること
      <img width="1383" alt="スクリーンショット 2023-12-17 16 24 05" src="https://github.com/sugao-2211/stockListProject/assets/141313076/fe32ee08-a1d7-4c22-a44f-089751b9fb1f">

</details>

##

<details>
<summary>findById()メソッドのDB単体テスト</summary>

- findById()メソッドのDB単体テスト
    - idを指定したときに該当する在庫情報が取得できること
    - 存在しないidを指定したときに空のOptionalが返されること

  https://github.com/sugao-2211/stockListProject/blob/0b4cbbca4335d147a3c9863cffdb2ee60174b0f3/src/test/java/com/stock/stocklist/mapper/StockListMapperTest.java#L60-L76

- 実行結果
    - idを指定したときに該当する在庫情報が取得できること
      <img width="1411" alt="スクリーンショット 2023-12-18 22 22 07" src="https://github.com/sugao-2211/stockListProject/assets/141313076/b7fbf184-cec9-433b-97fa-7673e4ea4ea2">
    - 存在しないidを指定したときに空のOptionalが返されること
      <img width="1380" alt="スクリーンショット 2023-12-18 22 22 37" src="https://github.com/sugao-2211/stockListProject/assets/141313076/e985359c-66c5-4bae-adb0-6bd69371ba67">

</details>

</details>

***

### Create処理の実装

<details>
<summary>Create処理</summary>

以下の処理を実行

- データ登録
    - name: 硫化ナトリウム九水和物
    - grade: 特級
    - quantity: 500
    - unit: g
    - purchase: 2023-08-12
- 例外処理の確認１ (MethodArgumentNotValidException)
    - nameを空文字で入力
    - gradeを空文字で入力
    - quantityを0で入力
    - unitを空文字で入力
    - purchaseを空文字で入力
- 例外処理の確認２ (MethodArgumentNotValidException)
    - nameを101文字で入力
    - purchaseを未来の日付で入力
    - quantityを空文字で入力
- 例外処理の確認３ (HttpMessageNotReadableException)
    - quantityを文字列で入力した場合
    - quantityを小数で入力した場合
    - purchaseの形式が誤っている場合

##

<details>
<summary>データ登録</summary>

- データ登録
    - curlコマンド
       ```
       curl --location 'http://localhost:8080/stockList' \
       --header 'Content-Type: application/json' \
       --data '{
        "name": "硫化ナトリウム九水和物",
        "grade": "特級",
        "quantity": "500",
        "unit": "g",
        "purchase": "2023-08-12"
       }'
       ```
    - 実行結果(Postman)  
      <img width="691" alt="スクリーンショット 2023-11-05 13 06 03" src="https://github.com/sugao-2211/stockListProject/assets/141313076/f84deb46-5425-46b1-8bf1-01d9c3dc9303">
    - 実行結果(SQL)  
      <img width="826" alt="スクリーンショット 2023-11-05 13 08 49" src="https://github.com/sugao-2211/stockListProject/assets/141313076/deedd02d-ccd9-4d28-a66d-6ecb76309742">

</details>

##

### 例外処理の確認

- バリデーションは以下のコードを記述  
  https://github.com/sugao-2211/stockListProject/blob/298d4015b43313a869b09a04d2cdf652d1617625/src/main/java/com/stock/stocklist/controller/request/InsertRequest.java#L17-L34

- 例外処理は以下のコードで実施  
  https://github.com/sugao-2211/stockListProject/blob/298d4015b43313a869b09a04d2cdf652d1617625/src/main/java/com/stock/stocklist/controller/ExceptionHandlerController.java#L34-L47  
  https://github.com/sugao-2211/stockListProject/blob/cd34c4b35a55664394e89476c91cc0b2ff8e74fe/src/main/java/com/stock/stocklist/controller/ExceptionHandlerController.java#L62-L70


- 例外処理は以下の内容で実施。
    - `@DateTimeFormat(pattern = "yyyy-MM-dd")`以外は`MethodArgumentNotValidException`で処理。
    - `@DateTimeFormat(pattern = "yyyy-MM-dd")`は`HttpMessageNotReadableException`で処理。
    - `quantity`の入力内容が`int`型に合致しない場合は`HttpMessageNotReadableException`で処理。

##

<details>
<summary>例外処理の確認１ (MethodArgumentNotValidException)</summary>

- 例外処理の確認１ (MethodArgumentNotValidException)
    - nameを空文字で入力
    - gradeを空文字で入力
    - quantityを0で入力
    - unitを空文字で入力
    - purchaseを空文字で入力
- 実行結果  
  <img width="698" alt="スクリーンショット 2023-11-05 12 47 10" src="https://github.com/sugao-2211/stockListProject/assets/141313076/d60e9ff2-3005-41eb-913a-0e91e7029c4f">
  <img width="698" alt="スクリーンショット 2023-11-05 12 47 34" src="https://github.com/sugao-2211/stockListProject/assets/141313076/6933dd4f-d607-4456-8a87-fb43a2045db5">

</details>

##

<details>
<summary>例外処理の確認２ (MethodArgumentNotValidException)</summary>

- 例外処理の確認２ (MethodArgumentNotValidException)
    - nameを101文字で入力
    - purchaseを未来の日付で入力
    - quantityを空文字で入力
- 実行結果
    - nameを101文字で入力
    - purchaseを未来の日付で入力
      <img width="885" alt="スクリーンショット 2023-11-05 12 49 22" src="https://github.com/sugao-2211/stockListProject/assets/141313076/9204af1f-0034-421b-ae5e-c00c1b3f677e">
    - quantityを空文字で入力
      <img width="693" alt="スクリーンショット 2023-11-07 21 37 53" src="https://github.com/sugao-2211/stockListProject/assets/141313076/6a47363d-7592-450a-8e68-c14c99acf10a">

</details>

##

<details>
<summary>例外処理の確認３ (HttpMessageNotReadableException)</summary>

- 例外処理の確認３ (HttpMessageNotReadableException)
    - quantityを文字列で入力した場合
    - quantityを小数で入力した場合
    - purchaseの形式が誤っている場合
- 実行結果
    - quantityを文字列で入力した場合  
      <img width="683" alt="スクリーンショット 2023-11-05 13 04 41" src="https://github.com/sugao-2211/stockListProject/assets/141313076/c52d7c6e-07c4-4d81-b8f4-771da0292d74"><br>
    - quantityを小数で入力した場合  
      <img width="685" alt="スクリーンショット 2023-11-05 13 43 55" src="https://github.com/sugao-2211/stockListProject/assets/141313076/d68623c7-851a-4945-bca2-cd2762c7a181"><br>
    - purchaseの形式が誤っている場合  
      <img width="695" alt="スクリーンショット 2023-11-05 12 56 09" src="https://github.com/sugao-2211/stockListProject/assets/141313076/5b48d694-eade-4281-bbc2-bb9357874bb2"><br>

</details>

</details>

***

### Update処理の実装

<details>
<summary>Update処理</summary>

以下の処理を実行

- データ更新
    - id: 1
    - name: エタノール(95)
    - grade: 特級
    - quantity: 500
    - unit: ｍL
    - purchase: 2023-09-30
- 例外処理の確認１ (NotFoundException)
    - 存在しないデータの更新
- 例外処理の確認２ (MethodArgumentNotValidException)
    - nameを空文字で入力
    - gradeを空文字で入力
    - quantityを空文字で入力
    - unitを空文字で入力
    - purchaseを空文字で入力
- 例外処理の確認３ (MethodArgumentNotValidException)
    - nameを101文字で入力
    - quantityを0で入力
    - purchaseを未来の日付で入力
- 例外処理の確認４ (HttpMessageNotReadableException)
    - quantityを文字列で入力した場合
    - quantityを小数で入力した場合
    - purchaseの形式が誤っている場合

##

<details>
<summary>データ更新</summary>

- データ更新
    - curlコマンド
       ```
      curl --location --request PATCH 'http://localhost:8080/stockList/1' \
      --header 'Content-Type: application/json' \
      --data '{
        "name": "エタノール(95)",
        "grade": "特級",
        "quantity": "500",
        "unit": "mL",
        "purchase": "2023-09-30"
      }'
      ```

    - 実行結果(Postman)
      <img width="693" alt="スクリーンショット 2023-11-07 21 14 47" src="https://github.com/sugao-2211/stockListProject/assets/141313076/3ccbd7a4-f68b-4504-ad90-8cf386c0b6c5">
    - 実行結果(SQL)  
      <img width="782" alt="スクリーンショット 2023-11-07 21 16 45" src="https://github.com/sugao-2211/stockListProject/assets/141313076/0e7b6266-0dd9-484b-9f60-5418cd5f946f">

</details>

##

### 例外処理の確認

- バリデーションは以下のコードを記述  
  https://github.com/sugao-2211/stockListProject/blob/cbc95ec5c3e328702519e88b0b54de0de67e26f9/src/main/java/com/stock/stocklist/controller/request/UpdateRequest.java#L17-L34

- 例外処理は以下のコードで実施  
  https://github.com/sugao-2211/stockListProject/blob/298d4015b43313a869b09a04d2cdf652d1617625/src/main/java/com/stock/stocklist/controller/ExceptionHandlerController.java#L22-L47  
  https://github.com/sugao-2211/stockListProject/blob/cd34c4b35a55664394e89476c91cc0b2ff8e74fe/src/main/java/com/stock/stocklist/controller/ExceptionHandlerController.java#L62-L70

- 例外処理は以下の内容で実施。
    - 存在しないデータを更新しようとした場合に`NotFoundException`で処理
    - `@DateTimeFormat(pattern = "yyyy-MM-dd")`以外は`MethodArgumentNotValidException`で処理
    - `@DateTimeFormat(pattern = "yyyy-MM-dd")`は`HttpMessageNotReadableException`で処理
    - `quantity`の入力内容が`int`型に合致しない場合は`HttpMessageNotReadableException`で処理

##

<details>
<summary>例外処理の確認１ (NotFoundException)</summary>

- 例外処理の確認１ (NotFoundException)
    - 存在しないデータの更新(id：99を更新するリクエスト)

- 実行結果  
  <img width="683" alt="スクリーンショット 2023-11-07 22 03 22" src="https://github.com/sugao-2211/stockListProject/assets/141313076/c47b28bb-8b1c-4d88-8487-f9512ea67a97">

</details>

##

<details>
<summary>例外処理の確認２ (MethodArgumentNotValidException)</summary>

- 例外処理の確認２ (MethodArgumentNotValidException)
    - nameを空文字で入力
    - gradeを空文字で入力
    - quantityを空文字で入力
    - unitを空文字で入力
    - purchaseを空文字で入力
- 実行結果  
  <img width="687" alt="スクリーンショット 2023-11-07 21 19 45" src="https://github.com/sugao-2211/stockListProject/assets/141313076/a8e59c55-dede-4e8b-8afa-7bc17a9087bc">
  <img width="693" alt="スクリーンショット 2023-11-07 21 20 02" src="https://github.com/sugao-2211/stockListProject/assets/141313076/0a599f62-7302-41d2-910a-47d8d4191a8a">

</details>

##

<details>
<summary>例外処理の確認３ (MethodArgumentNotValidException)</summary>

- 例外処理の確認３ (MethodArgumentNotValidException)
    - nameを101文字で入力
    - quantityを0で入力
    - purchaseを未来の日付で入力
- 実行結果
  <img width="897" alt="スクリーンショット 2023-11-07 21 34 46" src="https://github.com/sugao-2211/stockListProject/assets/141313076/3ceff859-04f5-4d3b-b3f4-83ee9d98998d">

</details>

##

<details>
<summary>例外処理の確認４ (HttpMessageNotReadableException)</summary>

- 例外処理の確認４ (HttpMessageNotReadableException)
    - quantityを文字列で入力した場合
    - quantityを小数で入力した場合
    - purchaseの形式が誤っている場合
- 実行結果
    - quantityを文字列で入力した場合  
      <img width="696" alt="スクリーンショット 2023-11-07 21 30 48" src="https://github.com/sugao-2211/stockListProject/assets/141313076/9842c185-44fc-412c-a0ab-c6cf7e89e139">
    - quantityを小数で入力した場合
      <img width="691" alt="スクリーンショット 2023-11-07 21 31 12" src="https://github.com/sugao-2211/stockListProject/assets/141313076/00351f4b-c7d0-4e1d-ada1-78212d21f292">
    - purchaseの形式が誤っている場合
      <img width="697" alt="スクリーンショット 2023-11-07 21 31 40" src="https://github.com/sugao-2211/stockListProject/assets/141313076/15cfe396-0ddb-400e-8b34-ef21f7d3e125">

</details>

</details>

***

### Delete処理の実装

<details>
<summary>Delete処理</summary>

以下の処理を実行

- データ削除
    - id: 6
- 例外処理の確認 (NotFoundException)
    - 存在しないデータの削除

##

<details>
<summary>データ削除</summary>

- データ削除
    - curlコマンド
   ```
  curl --location --request DELETE 'http://localhost:8080/stockList/6' \
  --data ''
  ```

    - 実行結果(Postman)
      <img width="689" alt="スクリーンショット 2023-11-08 22 36 30" src="https://github.com/sugao-2211/stockListProject/assets/141313076/769745c6-1a3f-48ee-9837-7d70f210cf28">
    - 実行結果(SQL)
      <img width="777" alt="スクリーンショット 2023-11-08 22 36 57" src="https://github.com/sugao-2211/stockListProject/assets/141313076/ee90d844-2424-4f04-aa09-e2378f1aba4c">

</details>

##

### 例外処理の確認

- 例外処理は以下のコードで実施  
  https://github.com/sugao-2211/stockListProject/blob/4f22c0856b96f510d6836cab8f6cdef711aa1d53/src/main/java/com/stock/stocklist/controller/ExceptionHandlerController.java#L22-L32

- 例外処理は以下の内容で実施。
    - 存在しないデータを更新しようとした場合に`NotFoundException`で処理

##

<details>
<summary>例外処理の確認 (NotFoundException)</summary>

- 例外処理の確認 (NotFoundException)
    - 存在しないデータの削除(id：99を削除するリクエスト)
- 実行結果  
  <img width="689" alt="スクリーンショット 2023-11-08 22 44 02" src="https://github.com/sugao-2211/stockListProject/assets/141313076/a959bd7f-0efc-4dda-8599-0af476b6e734">

</details>

</details>
