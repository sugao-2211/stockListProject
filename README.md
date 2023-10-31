## CRUD処理すべてを備えたREST APIの作成

### 概要

消耗品の在庫一覧についてAPIを作成しました。  
今回は試験研究などで使用される試薬を題材にして在庫一覧表を作成しました。

APIの内容

- Read処理
    - 全件検索及び部分一致検索(クエリパラメータ)の実装
    - id検索(パスパラメータ検索)の実装
    - 1000以上の数字を3桁区切りで表示(1,000)する変換処理を実装
- Create処理
    - 今後実装予定
- Update処理
    - 今後実装予定
- Delete処理
    - 今後実装予定

データベース作成時の内容  
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
- パスパラメータ(id)に合致するものを検索  
  id「4」で検索

---

- 全件検索
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList'
      ```
    - 実行結果
<img width="1012" alt="スクリーンショット 2023-10-31 13 33 02" src="https://github.com/sugao-2211/stockListProject/assets/141313076/028da7e0-c77a-4c33-acfc-0e05c0a5d1bd">
<img width="1010" alt="スクリーンショット 2023-10-31 13 33 23" src="https://github.com/sugao-2211/stockListProject/assets/141313076/a0430fdd-3ffd-4595-8ce0-2f9176e4b1a2">


- 部分一致検索(クエリパラメータ検索)
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList?name=%E7%A1%AB%E9%85%B8'
      ```
      硫酸のエンコード：%E7%A1%AB%E9%85%B8
    - 実行結果
<img width="1014" alt="スクリーンショット 2023-10-31 13 36 14" src="https://github.com/sugao-2211/stockListProject/assets/141313076/8431d332-9193-43a1-ba17-0da8de9caaa8">  

- id検索(パスパラメータ検索)
    - curlコマンド
      ```
      curl --location 'http://localhost:8080/stockList/4'
      ```
    - 実行結果  
<img width="1020" alt="スクリーンショット 2023-10-31 14 14 18" src="https://github.com/sugao-2211/stockListProject/assets/141313076/74df72a1-4d08-4210-b0de-8ceb9c2e8a3d">


***

### Create処理の実装

***

### Update処理の実装

***

### Delete処理の実装



