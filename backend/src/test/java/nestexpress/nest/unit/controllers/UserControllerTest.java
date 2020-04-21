package nestexpress.nest.unit.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nestexpress.nest.controllers.UserController;
import nestexpress.nest.exceptions.SessionExceptions;
import nestexpress.nest.entity.User;
import nestexpress.nest.services.UserService;
import nestexpress.nest.session.SessionTrack;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @MockBean
    private SessionTrack mockSessionTrack;

    @Mock
    private User mockUser;

    @Mock
    private HttpSession mockSession;

    @Mock
    private MockHttpServletRequest mockRequest;

    @Before
    public void setUp() {
        when(mockRequest.getSession(false)).thenReturn(mockSession);
    }

    @Ignore
    @Test
    // TODO: Fix this test to check if the user is active
    public void getActiveUserTest() throws Exception {

        when(mockRequest.getSession(false)).thenReturn(mockSession);

        when(mockSession.getAttribute("testUserId")).thenReturn(5);
        when(mockUser.getUsername()).thenReturn("test");
        when(mockUser.getBalance()).thenReturn(12.56);

        mockMvc.perform(get("/userAccount/currentUser"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", "test"))
                .andExpect(model().attribute("balance", 12.56));
    }

    @Test
    // Test not having an active session when requesting an active user
    public void getActiveUserSessionExceptionTest() throws Exception{
        when(mockRequest.getSession()).thenThrow(new SessionExceptions("Session Invalid"));

        mockMvc.perform(get("/userAccount/currentUser"))
                .andExpect(status().isConflict());

    }

    @Ignore
    @Test
    // TODO: Test that adding funds to an account
    public void addNewFundsTest() throws Exception {

        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockRequest.getSession()).thenReturn(mockSession);

        when(mockSession.getAttribute("userBalance")).thenReturn(12.00);

        when(mockUserService.findById(1)).thenReturn(mockUser);

        when(mockUser.getBalance()).thenReturn(10.00);

        mockMvc.perform(post("/userAccount/addFunds/{funds}", 12.00))
                .andExpect(status().isOk());
    }

    @Ignore
    @Test
    // TODO: Tes the logging out of a User
    public void logoutActiveUserTest() throws Exception {
        mockRequest.setSession(mockSession);

        assertNotNull(mockRequest.getSession());

        mockMvc.perform(get("/userAccount/logoutUser"))
                .andExpect(status().isOk());
    }
}