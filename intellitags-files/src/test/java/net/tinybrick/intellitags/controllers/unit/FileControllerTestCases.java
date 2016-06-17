package net.tinybrick.intellitags.controllers.unit;

import net.tinybrick.intellitags.IntellitagsMain;
import net.tinybrick.test.web.unit.ControllerTestBase;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wangji on 2016/6/15.
 */
@SpringApplicationConfiguration(classes = IntellitagsMain.class)
//@TestPropertySource(locations = "classpath:config/test.properties")
public class FileControllerTestCases extends ControllerTestBase{
    @Test
    public void testGetTicket() throws Exception {
        ResultActions resultActions;
        resultActions = GET("/v1.0/ticket", MediaType.ALL, MediaType.APPLICATION_JSON);
        resultActions.andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void testList() throws Exception {
        ResultActions resultActions;
        resultActions = GET("/v1.0/list", MediaType.ALL, MediaType.APPLICATION_JSON);
        resultActions.andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testPostSingleFile() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("file", "filename.txt", "text/plain", "some data".getBytes());

        ResultActions resultActions =  mockMvc.perform(fileUpload("/v1.0/file").file(mockFile).session(session)
                .contentType(MediaType.MULTIPART_FORM_DATA).accept(MediaType.APPLICATION_JSON));
        resultActions.andDo(print()).andExpect(status().isCreated());
    }
}
