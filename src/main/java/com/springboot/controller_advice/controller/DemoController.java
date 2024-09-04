package com.springboot.controller_advice.controller;

import org.springframework.http.HttpStatus; // Imports the HttpStatus enumeration for specifying HTTP status codes.
import org.springframework.http.ResponseEntity; // Imports the ResponseEntity class for returning HTTP responses.
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*; // Imports annotations for mapping HTTP requests to controller methods.

import com.springboot.controller_advice.dto.UserDto;

import jakarta.validation.Valid;

import java.util.HashMap; // Imports the HashMap class for managing key-value pairs.
import java.util.Map; // Imports the Map interface for working with mappings.
import java.util.Optional; // Imports the Optional class to handle nullable return values.

@CrossOrigin 
@RestController // Indicates that this class serves as a RESTful controller.
@RequestMapping("/api") // Specifies that all endpoints in this controller will be prefixed with "/api".
public class DemoController {

    private Map<Integer, String> dataStore = new HashMap<>(); // A simple in-memory data store using a map.

    @GetMapping("/items") // Maps HTTP GET requests to /api/items/{id} to this method.
    public ResponseEntity<String> getItems() {

        String e = "e";

        // Checks if the item is present; if not, returns a 404 Not Found response.
        return new ResponseEntity<String>(e, HttpStatus.OK);
    }

    /**
     * Handles GET requests to retrieve a resource by ID.
     *
     * @param id the ID of the resource to retrieve
     * @return ResponseEntity containing the resource or a not found error
     * 
     *         Example curl command:
     *         curl -X GET http://localhost:8080/api/items/1
     */
    @GetMapping("/items/{id}") // Maps HTTP GET requests to /api/items/{id} to this method.
    public ResponseEntity<String> getItem(@PathVariable int id) {
        // Retrieves the item from the data store by ID.
        Optional<String> item = Optional.ofNullable(dataStore.get(id));

        // Checks if the item is present; if not, returns a 404 Not Found response.
        return item.map(ResponseEntity::ok) // Returns 200 OK with the item if found.
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found"));
    }

    /**
     * Handles POST requests to create a new resource.
     *
     * @param id    the ID of the new resource
     * @param value the value of the new resource
     * @return ResponseEntity containing a success message or a conflict error
     * 
     *         Example curl command:
     *         curl -X POST http://localhost:8080/api/items -d "id=1&value=SampleItem"
     */
    @PostMapping("/items") // Maps HTTP POST requests to /api/items to this method.
    public ResponseEntity<String> createItem(@Valid @RequestBody UserDto value) {
        // Checks if the item already exists in the data store.
        if (dataStore.containsKey(value.getId())) {
            // Returns a 409 Conflict status if the item already exists.
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Item already exists");
        }
        // Adds the new item to the data store.
        dataStore.put(value.getId(), value.getFirstName());
        // Returns a 201 Created status with a success message.
        return ResponseEntity.status(HttpStatus.CREATED).body("Item created successfully");
    }

    /**
     * Handles PUT requests to update an existing resource.
     *
     * @param id    the ID of the resource to update
     * @param value the new value for the resource
     * @return ResponseEntity containing a success message or a not found error
     * 
     *         Example curl command:
     *         curl -X PUT http://localhost:8080/api/items/1 -d "value=Updated Item"
     */
    @PutMapping("/items/{id}") // Maps HTTP PUT requests to /api/items/{id} to this method.
    public ResponseEntity<String> updateItem(@PathVariable int id, @RequestParam String value) {
        // Checks if the item exists in the data store.
        if (!dataStore.containsKey(id)) {
            // Returns a 404 Not Found status if the item does not exist.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
        // Updates the item in the data store with the new value.
        dataStore.put(id, value);
        // Returns a 200 OK status with a success message.
        return ResponseEntity.ok("Item updated successfully");
    }

    /**
     * Handles DELETE requests to remove a resource by ID.
     *
     * @param id the ID of the resource to delete
     * @return ResponseEntity containing a success message or a not found error
     * 
     *         Example curl command:
     *         curl -X DELETE http://localhost:8080/api/items/1
     */
    @DeleteMapping("/items/{id}") // Maps HTTP DELETE requests to /api/items/{id} to this method.
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        // Checks if the item exists in the data store.
        if (!dataStore.containsKey(id)) {
            // Returns a 404 Not Found status if the item does not exist.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
        // Removes the item from the data store.
        dataStore.remove(id);
        // Returns a 200 OK status with a success message.
        return ResponseEntity.ok("Item deleted successfully");
    }
}
