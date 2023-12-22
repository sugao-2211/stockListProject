package com.stock.stock.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
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

}
