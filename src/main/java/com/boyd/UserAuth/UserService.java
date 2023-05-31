package com.boyd.UserAuth;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Service
public class UserService {
    private static final String XML_FILE_PATH = "C://Projects//Week7//UserAuth//users.xml";

    /**
     * Authenticates a user by checking the local XML file for the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the user is valid, False otherwise.
     * @throws Exception If an error occurs during XML file parsing or reading.
     */
    public boolean authenticateUser(String username, String password) throws Exception {
        UserList userList = getUsersFromXml();
        for (User user : userList.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registers a new user by persisting the user information to the local XML file.
     *
     * @param user The user information to be registered.
     * @return True if the user is successfully registered, False if the username already exists.
     * @throws Exception If an error occurs during XML file parsing or writing.
     */
    public boolean registerUser(User user) throws Exception {
        UserList userList = getUsersFromXml();
        for (User existingUser : userList.getUsers()) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                return false; // User already exists
            }
        }
        userList.getUsers().add(user);
        saveUsersToXml(userList);
        return true;
    }

    /**
     * Retrieves the list of users from the XML file.
     *
     * @return The UserList object containing the list of users.
     * @throws Exception If an error occurs during XML file parsing or reading.
     */
    private UserList getUsersFromXml() throws Exception {
        File xmlFile = new File(XML_FILE_PATH);
        JAXBContext jaxbContext = JAXBContext.newInstance(UserList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (UserList) jaxbUnmarshaller.unmarshal(xmlFile);
    }

    /**
     * Saves the list of users to the XML file.
     *
     * @param userList The UserList object containing the list of users.
     * @throws Exception If an error occurs during XML file parsing or writing.
     */
    private void saveUsersToXml(UserList userList) throws Exception {
        File xmlFile = new File(XML_FILE_PATH);
        JAXBContext jaxbContext = JAXBContext.newInstance(UserList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(userList, xmlFile);
    }
}