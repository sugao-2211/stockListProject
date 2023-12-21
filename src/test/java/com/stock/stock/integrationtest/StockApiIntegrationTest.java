package com.stock.stock.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
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
    void 全件の在庫情報が取得できること() throws Exception {
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
                  }
                ]
                  """, response, JSONCompareMode.STRICT);

    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在する在庫名の在庫情報が取得できること() throws Exception {
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
                  }
                ]
                  """, response, JSONCompareMode.STRICT);

    }

}
