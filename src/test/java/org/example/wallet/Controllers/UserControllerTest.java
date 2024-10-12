package org.example.wallet.Controllers;

public class UserControllerTest {

//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testRegister_Success() {
//        Map<String, String> userMap = new HashMap<>();
//        userMap.put("name", "testUser");
//        userMap.put("password", "testPassword");
//
//        when(userService.registerUser("testUser", "testPassword")).thenReturn(1);
//
//        ResponseEntity<Object> response = userController.register(userMap);
//
//        assertEquals(200, response.getStatusCode().value());
//        assertEquals(1, response.getBody());
//    }
//
//    @Test
//    public void testRegister_Failure() {
//        Map<String, String> userMap = new HashMap<>();
//        userMap.put("name", "testUser");
//        userMap.put("password", "testPassword");
//
//        when(userService.registerUser("testUser", "testPassword")).thenThrow(new RuntimeException("Registration failed"));
//
//        ResponseEntity<Object> response = userController.register(userMap);
//
//        assertEquals(500, response.getStatusCode().value());
//        assertEquals(-1, response.getBody());
//    }
}