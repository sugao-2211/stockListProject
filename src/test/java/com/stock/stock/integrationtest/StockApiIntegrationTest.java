package com.stock.stock.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StockApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫名を指定しなかったときにステータスコード200が返され全件の在庫情報を取得すること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                [
                  {
                   "id": 1,
                   "name": "メタノール",
                   "grade": "HPLC用",
                   "quantity": "3",
                   "unit": "L",
                   "purchase": "2023-05-24"
                  },
                  {
                   "id": 2,
                   "name": "塩化カリウム",
                   "grade": "特級",
                   "quantity": "500",
                   "unit": "g",
                   "purchase": "2023-07-19"
                  },
                  {
                   "id": 3,
                   "name": "硫酸ナトリウム",
                   "grade": "特級",
                   "quantity": "5",
                   "unit": "kg",
                   "purchase": "2022-08-30"
                  },
                  {
                   "id": 4,
                   "name": "グルコアミラーゼ",
                   "grade": "生化学用",
                   "quantity": "10,000",
                   "unit": "unit",
                   "purchase": "2023-10-11"
                  },
                  {
                   "id": 5,
                   "name": "硫酸",
                   "grade": "硫酸呈色用",
                   "quantity": "500",
                   "unit": "mL",
                   "purchase": "2023-04-05"
                  },
                  {
                   "id": 6,
                   "name": "ピリドキシン塩酸塩",
                   "grade": "日本薬局方標準品",
                   "quantity": "200",
                   "unit": "mg",
                   "purchase": "2023-09-22"
                  },
                  {
                   "id": 7,
                   "name": "亜硫酸ナトリウム",
                   "grade": "特級",
                   "quantity": "25",
                   "unit": "g",
                   "purchase": "2023-07-07"
                  },
                  {
                   "id": 8,
                   "name": "アミド硫酸",
                   "grade": "認証標準物質",
                   "quantity": "50",
                   "unit": "g",
                   "purchase": "2023-04-15"
                  }
                ]
                  """, response, JSONCompareMode.STRICT);

    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在する在庫名を指定したときにステータスコード200が返され該当する在庫情報を取得すること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stock?name=硫酸"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                [
                  {
                   "id": 3,
                   "name": "硫酸ナトリウム",
                   "grade": "特級",
                   "quantity": "5",
                   "unit": "kg",
                   "purchase": "2022-08-30"
                  },
                  {
                   "id": 5,
                   "name": "硫酸",
                   "grade": "硫酸呈色用",
                   "quantity": "500",
                   "unit": "mL",
                   "purchase": "2023-04-05"
                  },
                  {
                   "id": 7,
                   "name": "亜硫酸ナトリウム",
                   "grade": "特級",
                   "quantity": "25",
                   "unit": "g",
                   "purchase": "2023-07-07"
                  },
                  {
                   "id": 8,
                   "name": "アミド硫酸",
                   "grade": "認証標準物質",
                   "quantity": "50",
                   "unit": "g",
                   "purchase": "2023-04-15"
                  }
                ]
                  """, response, JSONCompareMode.STRICT);

    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しない在庫名を指定したときにステータスコード200が返され空のリストを取得すること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stock?name=硝酸"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                []
                  """, response, JSONCompareMode.STRICT);

    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在するidを指定したときにステータスコード200が返され該当する在庫情報を取得すること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stock/4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                 "id": 4,
                 "name": "グルコアミラーゼ",
                 "grade": "生化学用",
                 "quantity": "10,000",
                 "unit": "unit",
                 "purchase": "2023-10-11"
                }
                """, response, JSONCompareMode.STRICT);

    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しないidを指定したときにステータスコード404が返されること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stock/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                 "message" :"data not found",
                 "timestamp":"2023-12-21T12:00:00.511021+09:00[Asia/Tokyo]",
                 "error":"Not Found",
                 "path":"/stock/99",
                 "status":"404"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("timestamp", ((o1, o2) -> true))));

    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @ExpectedDataSet(value = "datasets/stockList_insert.yml", ignoreCols = "id")
    @Transactional
    void 新規の在庫情報が登録できること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "message": "new data created"
                 }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("newId", ((o1, o2) -> true))));
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にnameが空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "",
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"name","message":"1文字以上、100文字以内で入力してください"},
                                {"field":"name","message":"試薬名が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にnameがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": null,
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"name","message":"試薬名が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にnameが101文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"name","message":"1文字以上、100文字以内で入力してください"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にgradeが空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "",
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"grade","message":"等級が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にgradeがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": null,
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"grade","message":"等級が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にquantityが空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": "",
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"quantity","message":"1以上の値を入力してください"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にquantityがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": null,
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"quantity","message":"1以上の値を入力してください"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にquantityが0のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity":0,
                                         "unit": "g",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"quantity","message":"1以上の値を入力してください"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にquantityが文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": "a",
                                         "unit": "g",
                                         "purchase": "2022-12-11"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "request error",
                   "error":{
                                "message":"いずれかの入力形式が正しくありません。"
                            }
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にunitが空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": "",
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"unit","message":"単位が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にunitがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": null,
                                         "purchase": "2024-02-23"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"unit","message":"単位が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にpurchaseがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": null
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"purchase","message":"購入日が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にpurchaseが未来の日付のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": "2025-12-11"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"purchase","message":"must be a date in the past or in the present"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新規の在庫情報を登録する際にpurchaseの形式が正しくないときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.post("/stock")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                         "name": "硫化ナトリウム九水和物",
                                         "grade": "特級",
                                         "quantity": 500,
                                         "unit": "g",
                                         "purchase": "202212-11"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "request error",
                   "error":{
                                "message":"いずれかの入力形式が正しくありません。"
                            }
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @ExpectedDataSet(value = "datasets/stockList_update.yml")
    @Transactional
    void 在庫情報が更新できること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": 500,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "message": "data updated"
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しない在庫情報を更新しようとしたときにステータスコード404及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/99")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": 500,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "path":"/stock/99",
                   "error":"Not Found",
                   "timestamp":"2024-07-14T12:00:00.511021+09:00[Asia/Tokyo]",
                   "message": "data not found",
                   "status": "404"
                 }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("timestamp", ((o1, o2) -> true))));
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にnameを空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "",
                                            "grade": "特級",
                                            "quantity": 500,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"name","message":"試薬名が入力されていません"},
                                {"field":"name","message":"1文字以上、100文字以内で入力してください"}                   
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にnameがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": null,
                                            "grade": "特級",
                                            "quantity": 500,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"name","message":"試薬名が入力されていません"}               
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にnameが101文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                                            "grade": "特級",
                                            "quantity": 500,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"name","message":"1文字以上、100文字以内で入力してください"}                   
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にgradeが空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "",
                                            "quantity": 500,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"grade","message":"等級が入力されていません"}               
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にgradeがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade":null,
                                            "quantity": 500,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"grade","message":"等級が入力されていません"}               
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にquantityが空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": "",
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"quantity","message":"1以上の値を入力してください"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にquantityがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": null,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"quantity","message":"1以上の値を入力してください"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にquantityが0のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": null,
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"quantity","message":"1以上の値を入力してください"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にquantityが文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": "a",
                                            "unit": "mL",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "request error",
                   "error":{
                                "message":"いずれかの入力形式が正しくありません。"
                            }
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にunitが空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": "500",
                                            "unit": "",
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"unit","message":"単位が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にunitがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": "500",
                                            "unit": null,
                                            "purchase": "2023-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"unit","message":"単位が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にpurchaseが空文字のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": "500",
                                            "unit": "mL",
                                            "purchase": ""
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"purchase","message":"購入日が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にpurchaseがnullのときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": "500",
                                            "unit": "mL",
                                            "purchase": null
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"purchase","message":"購入日が入力されていません"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にpurchaseが未来の日付のときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": "500",
                                            "unit": "mL",
                                            "purchase": "2025-09-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                  "status": "BAD_REQUEST",
                   "message": "validation error",
                   "errors":[
                                {"field":"purchase","message":"must be a date in the past or in the present"}
                            ]
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫情報を更新する際にpurchaseが形式が正しくないときにステータスコード400及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.patch("/stock/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("""
                                        {
                                            "name": "エタノール(95)",
                                            "grade": "特級",
                                            "quantity": "500",
                                            "unit": "mL",
                                            "purchase": "202509-30"
                                        }
                                        """))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                  "status": "BAD_REQUEST",
                   "message": "request error",
                   "error":{
                                "message":"いずれかの入力形式が正しくありません。"
                            }
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @ExpectedDataSet(value = "datasets/stockList_delete.yml")
    @Transactional
    void 在庫情報を削除できること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.delete("/stock/6"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                  "message":"data deleted"
                 }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しない在庫情報を削除しようとしたときにステータスコード404及びエラーに応じたメッセージが返されること() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders.delete("/stock/99"))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                 {
                   "path":"/stock/99",
                   "error":"Not Found",
                   "timestamp":"2024-07-14T12:00:00.511021+09:00[Asia/Tokyo]",
                   "message": "data not found",
                   "status": "404"
                 }
                """, response, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("timestamp", ((o1, o2) -> true))));
    }

}
