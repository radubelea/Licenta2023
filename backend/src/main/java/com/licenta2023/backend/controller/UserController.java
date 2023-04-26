package com.licenta2023.backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.licenta2023.backend.domain.User;
import com.licenta2023.backend.domain.authentication.AuthenticationRequest;
import com.licenta2023.backend.domain.authentication.AuthenticationResponse;
import com.licenta2023.backend.domain.registration.RegistrationRequest;
import com.licenta2023.backend.service.IUserService;
import com.licenta2023.backend.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.licenta2023.backend.utils.JwtTokenUtil.*;


@CrossOrigin
@RestController()
public class UserController {
    private static final String INVALID_CREDENTIALS_ERROR_MESSAGE = "Invalid email or password!";
    private static final String USERNAME_ALREADY_EXISTS_ERROR_MESSAGE = "Email already exists!";
    private static final String SUCCESS_MESSAGE = "Success!";
    private static final String INVALID_ID_ERROR_MESSAGE = "Invalid id!";
    private static final String INVALID_EMAIL_ERROR_MESSAGE = "Invalid email!";
    private static final String NO_SUCH_USERNAME_ERROR_MESSAGE = "User does not exist!";
    private static final String INVALID_PASSWORD_ERROR_MESSAGE = "Invalid password!";

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private Validator validator;
    @Autowired
    private IUserService userService;

    /**
     * url:    http://localhost:8080/licenta2023/registration
     * <p>
     * Create a new User with provided parameters
     *
     * @param request - the request object that needs to contain following fields
     *                name, firstName, lastName, password ,  as JSON object
     * @return -
     */
    @PostMapping(path = "registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        boolean passwordIsValid = validator.validatePassword(request.getPassword());
        boolean nameIsValid = validator.validateUsername(request.getName());
        if (!(passwordIsValid && nameIsValid)) {
            return new ResponseEntity<>(INVALID_CREDENTIALS_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        //see if a user with the same name already exists
        if (userService.findUserByEmail(request.getEmail()) != null) {
            return new ResponseEntity<>(USERNAME_ALREADY_EXISTS_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        //if email field exists, it must match the email pattern
        if (request.getEmail() != null && !validator.validateEmail(request.getEmail())) {
            return new ResponseEntity<>(INVALID_EMAIL_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(
                request.getEmail(),
                encodedPassword,
                request.getAge(),
                request.getName()
        );

        userService.save(user);
        System.out.println("Successfully added user " + user.getName());
        return new ResponseEntity<>(SUCCESS_MESSAGE, HttpStatus.OK);

    }


    @PostMapping(path = "authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        System.out.println(request.getEmail());
        User user = userService.findUserByEmail(request.getEmail());
        //no user with provided name or bad password
        if (user == null) {
            return new ResponseEntity<>(NO_SUCH_USERNAME_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(INVALID_PASSWORD_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        try {
            //generating token
            String jwtToken = createToken(user);
            return new ResponseEntity<>(new AuthenticationResponse(jwtToken), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * url:   http://localhost:8080/licenta2023/user
     * <p>
     * Provide access to current logged user data
     *
     * @return - the details of current user, if a valid token is present in header
     */
    @GetMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        if(!isValid(token)){
            return new ResponseEntity<>("Authorization is required!", HttpStatus.FORBIDDEN);
        }
        User user = (User) getTokenPayloadAsObject(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * url:   http://localhost:8080/licenta2023/users/{id}
     * <p>
     * Provide access to user data through user id
     *
     * @param id - user id
     * @return - the user which has the specified id , if exist
     * - an error message and 404 NOT_FOUND error message otherwise
     */
    @GetMapping(path = "users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = userService.findOne(id);
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(INVALID_ID_ERROR_MESSAGE, HttpStatus.NOT_FOUND);
    }
    //test method
    @GetMapping(path = "hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hello() {
        return new ResponseEntity<>("Hello",HttpStatus.OK);
        /*return "Hello";*/
    }

}
