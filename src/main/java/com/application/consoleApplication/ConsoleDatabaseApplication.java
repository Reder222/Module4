package com.application.consoleApplication;


import com.application.controllers.UserController;
import com.application.dto.UserDTO;
import com.application.dto.UserDTOUtil;
import com.application.service.UserService;
import com.application.util.ErrorValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ConsoleDatabaseApplication {

    InputOutputController inputOutputController;
    UserController userController;

    @Autowired
    public ConsoleDatabaseApplication(InputOutputController inputOutputController, UserController userController) {
        this.inputOutputController = inputOutputController;
        this.userController = userController;
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

        userController.create(dto);

        String message = ErrorValidationUtil.getError("UserData");

        if (message != null) {
            inputOutputController.showError(message);
        } else inputOutputController.showMessage("Success");

    }


    private void readEntries() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("All persistent entries:");
        userController.findAll().forEach(entry -> inputOutputController.showMessage(entry.toString()));
    }

    private void updateEntry() {
        inputOutputController.emptyLine();
        inputOutputController.showMessage("Input id:");

        int id = inputOutputController.readInt();
        UserDTO handledObject = userController.findById(id);

        if (handledObject == null) {
            inputOutputController.showError("Entry not found");
            return;
        }

        inputOutputController.showMessage(handledObject.toString());

        inputOutputController.showMessage("""
                What do you want to do?
                1 Change name
                2 Change email
                3 Change age
                4 Update and exit
                Input a number:""");

        while (true)
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

                    userController.update(handledObject.getId(), handledObject);

                    String message = ErrorValidationUtil.getError("UserData");
                    if (message != null) {
                        inputOutputController.showError(message);
                    } else inputOutputController.showMessage("Success");
                    inputOutputController.emptyLine();
                    return;
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

        userController.deleteById(id);
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
