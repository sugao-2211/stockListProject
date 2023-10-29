DROP TABLE IF EXISTS stock_list;

CREATE TABLE stock_list (
  id int unsigned AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  grade VARCHAR(20) NOT NULL,
  quantity int unsigned NOT NULL,
  unit VARCHAR(20) NOT NULL,
  purchase DATE NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO stock_list (id,name,grade,quantity,unit,purchase) VALUES (1,"メタノール","HPLC用",3,"L",'2023-05-24');
INSERT INTO stock_list (id,name,grade,quantity,unit,purchase) VALUES (2,"塩化カリウム","特級",500,"g",'2023-07-19');
INSERT INTO stock_list (id,name,grade,quantity,unit,purchase) VALUES (3,"硫酸ナトリウム","特級",5,"kg",'2022-08-30');
INSERT INTO stock_list (id,name,grade,quantity,unit,purchase) VALUES (4,"グルコアミラーぜ","生化学用",10000,"unit",'2023-10-11');
INSERT INTO stock_list (id,name,grade,quantity,unit,purchase) VALUES (5,"硫酸","硫酸呈色用",500,"mL",'2023-04-05');
INSERT INTO stock_list (id,name,grade,quantity,unit,purchase) VALUES (6,"ピリドキシン塩酸塩","日本薬局方標準品",200,"mg",'2023-09-22');


