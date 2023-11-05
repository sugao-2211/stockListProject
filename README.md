## CRUD処理すべてを備えたREST APIの作成

### 概要

消耗品の在庫一覧についてAPIを作成しました。  
今回は試験研究などで使用される試薬を題材にして在庫一覧表を作成しました。

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
    - 今後実装予定
- Delete処理
    - 今後実装予定

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

以下の処理を実行

- データベース全件検索
- クエリパラメータ(name)に合致するものを部分一致検索  
  「硫酸」で検索
- クエリパラメータ(name)の部分一致検索における例外処理  
  「硝酸」で検索し例外処理を発生
- パスパラメータ(id)に合致するものを検索  
  id「4」で検索
- パスパラメータ(id)の検索における例外処理  
  id「9」で検索し例外処理を発生

##

- 全件検索
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList'
      ```
    - 実行結果
      <img width="1012" alt="スクリーンショット 2023-10-31 13 33 02" src="https://github.com/sugao-2211/stockListProject/assets/141313076/028da7e0-c77a-4c33-acfc-0e05c0a5d1bd">
      <img width="1010" alt="スクリーンショット 2023-10-31 13 33 23" src="https://github.com/sugao-2211/stockListProject/assets/141313076/a0430fdd-3ffd-4595-8ce0-2f9176e4b1a2">

##

- 部分一致検索(クエリパラメータ検索)
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList?name=%E7%A1%AB%E9%85%B8'
      ```
      硫酸のエンコード：%E7%A1%AB%E9%85%B8
    - 実行結果
      <img width="1014" alt="スクリーンショット 2023-10-31 13 36 14" src="https://github.com/sugao-2211/stockListProject/assets/141313076/8431d332-9193-43a1-ba17-0da8de9caaa8">

##

- 部分一致検索(クエリパラメータ検索)における例外処理
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList?name=%E7%A1%9D%E9%85%B8'
      ```
      硝酸のエンコード：%E7%A1%9D%E9%85%B8
    - 実行結果
      <img width="1015" alt="スクリーンショット 2023-10-31 16 18 45" src="https://github.com/sugao-2211/stockListProject/assets/141313076/41c3481b-f62f-4f1e-8a0e-896d132e9ba3">

##

- id検索(パスパラメータ検索)
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList/4'
      ```
    - 実行結果  
      <img width="1020" alt="スクリーンショット 2023-10-31 14 14 18" src="https://github.com/sugao-2211/stockListProject/assets/141313076/74df72a1-4d08-4210-b0de-8ceb9c2e8a3d">

##

- id検索(パスパラメータ検索)における例外処理
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList/9'
      ```
    - 実行結果
      <img width="1004" alt="スクリーンショット 2023-10-31 16 17 58" src="https://github.com/sugao-2211/stockListProject/assets/141313076/119ed0ee-64ee-447a-befc-c185ded3dc94">

***

### Create処理の実装

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
- 例外処理の確認３ (HttpMessageNotReadableException)
    - quantityを文字列で入力した場合
    - quantityを小数で入力した場合
    - purchaseの形式が誤っている場合

##
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

##

### 例外処理の確認

- バリデーションは以下のコードを記述  
https://github.com/sugao-2211/stockListProject/blob/298d4015b43313a869b09a04d2cdf652d1617625/src/main/java/com/stock/stocklist/controller/request/InsertRequest.java#L17-L34

- 例外処理は以下のコードで実施
https://github.com/sugao-2211/stockListProject/blob/298d4015b43313a869b09a04d2cdf652d1617625/src/main/java/com/stock/stocklist/controller/ExceptionHandlerController.java#L34-L47 

- 例外処理は以下の内容で実施した。
  - `@DateTimeFormat(pattern = "yyyy-MM-dd")`以外は`MethodArgumentNotValidException`で処理する。
  - `@DateTimeFormat(pattern = "yyyy-MM-dd")`は`HttpMessageNotReadableException`で処理する。
  - `quantity`の入力内容が`int`型に合致しない場合は`HttpMessageNotReadableException`で処理する。

##

- 例外処理の確認１ (MethodArgumentNotValidException)
    - nameを空文字で入力
    - gradeを空文字で入力
    - quantityを0で入力
    - unitを空文字で入力
    - purchaseを空文字で入力
- 実行結果  
  <img width="698" alt="スクリーンショット 2023-11-05 12 47 10" src="https://github.com/sugao-2211/stockListProject/assets/141313076/d60e9ff2-3005-41eb-913a-0e91e7029c4f">
  <img width="698" alt="スクリーンショット 2023-11-05 12 47 34" src="https://github.com/sugao-2211/stockListProject/assets/141313076/6933dd4f-d607-4456-8a87-fb43a2045db5">

##

- 例外処理の確認２ (MethodArgumentNotValidException)
    - nameを101文字で入力
    - purchaseを未来の日付で入力
- 実行結果  
  <img width="885" alt="スクリーンショット 2023-11-05 12 49 22" src="https://github.com/sugao-2211/stockListProject/assets/141313076/9204af1f-0034-421b-ae5e-c00c1b3f677e">

##

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

***

### Update処理の実装

***

### Delete処理の実装



