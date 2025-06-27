package com.application.consoleApplication;


import com.application.controllers.UserController;
import com.application.dto.UserDTO;
import com.application.dto.UserDTOUtil;
import com.application.service.UserService;
import com.application.util.ErrorValidationUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsoleDatabaseApplication {

    InputOutputController inputOutputController;
    RestTemplate restTemplate;

    @Autowired
    public ConsoleDatabaseApplication(InputOutputController inputOutputController) {
        this.inputOutputController = inputOutputController;
        restTemplate = new RestTemplate();
    }

    private void showInterface() {
        String str = """
                Simple database console application
                Application supports CRUD commands
                
                What do you want to do?
                1 Create a new entry
                2 Read all entries
                3 Update an entry by id
                4 Delete an entry
                5 Change table
                6 Exit
                
                Input a number:
                """;
        inputOutputController.showMessage(str);
    }

    private void createEntry() {
        inputOutputController.emptyLine();

        inputOutputController.showMessage("Input name: ");
        String name = inputOutputController.readLine();
        inputOutputController.showMessage("Input email: ");
        String email = inputOutputController.readLine();
        inputOutputController.showMessage("Input Age: ");
        int age = inputOutputController.readInt();

        UserDTO dto = new UserDTO(name, email, age);

        restTemplate.put("http://localhost:8080/users/create", dto);

        /*if (response.getStatusCode() == HttpStatus.CREATED) {
            inputOutputController.showMessage("Entry created");
            return;
        }
        inputOutputController.showError(response.getBody().toString());
*/
    }


    private void readEntries() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("All persistent entries:");

        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/users/", List.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper mapper = new ObjectMapper();
            List<UserDTO> users = new ArrayList<>();

            try {
                users = mapper.readValue(mapper.writeValueAsString(response.getBody()), new TypeReference<>() {
                });

            } catch (Exception e) {
                inputOutputController.showError(e.getMessage());
            }
            users.forEach(System.out::println);
            return;
        }
        inputOutputController.showError(response.getBody().toString());
    }

    private void updateEntry() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("Input id:");

        int id = inputOutputController.readInt();

        ResponseEntity<UserDTO> entityResponse = restTemplate.getForEntity("http://localhost:8080/users/" + id, UserDTO.class);

        if (entityResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            inputOutputController.showError(entityResponse.getBody().toString());
        }

        UserDTO handledObject = entityResponse.getBody();

        inputOutputController.showMessage(handledObject.toString());

        inputOutputController.showMessage("""
                What do you want to do?
                1 Change name
                2 Change email
                3 Change age
                4 Update and exit
                Input a number:""");

        boolean isEditing = true;

        while (isEditing)
            switch (inputOutputController.readInt()) {
                case 1: {
                    inputOutputController.emptyLine();
                    inputOutputController.showMessage("Input name: ");
                    String name = inputOutputController.readLine();
                    handledObject.setName(name);
                    break;
                }
                case 2: {
                    inputOutputController.emptyLine();
                    inputOutputController.showMessage("Input email: ");
                    String email = inputOutputController.readLine();
                    handledObject.setEmail(email);
                    break;
                }
                case 3: {
                    inputOutputController.emptyLine();
                    inputOutputController.showMessage("Input age: ");
                    int age = inputOutputController.readInt();
                    handledObject.setAge(age);
                    break;
                }
                case 4: {

                    ResponseEntity<HttpStatus> response = restTemplate.postForEntity("http://localhost:8080/users/" + id + "/edit", handledObject, HttpStatus.class);

                    if (response.getStatusCode() == HttpStatus.OK) {
                        inputOutputController.showMessage("Entry updated");
                        isEditing = false;
                        break;
                    }

                    inputOutputController.showError(response.getBody().toString());
                    isEditing = false;
                    break;

                }
                default: {
                    inputOutputController.showError("Please enter a valid number");
                    break;
                }
            }


    }

    private void deleteEntry() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("Input id:");
        int id = inputOutputController.readInt();

        restTemplate.delete("http://localhost:8080/users/" + id);
    }

    private void changeTable() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("Not implemented yet");
    }

    private void exit() {
        Thread.currentThread().interrupt();
    }

    public void run() {
        while (!Thread.interrupted()) {
            showInterface();
            switch (inputOutputController.readInt()) {
                case 1: {
                    createEntry();
                    break;
                }
                case 2: {
                    readEntries();
                    break;
                }
                case 3: {
                    updateEntry();
                    break;
                }
                case 4: {
                    deleteEntry();
                    break;
                }
                case 5: {
                    changeTable();
                    break;
                }
                case 6: {
                    exit();
                    break;
                }
                default: {
                    inputOutputController.showError("Invalid command");
                    break;
                }
            }

        }

    }

}
