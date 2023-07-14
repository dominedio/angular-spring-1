package com.springcrud.user.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.validation.FieldError;

import com.springcrud.user.entities.User;
import com.springcrud.user.repositories.UserRepository;
import com.springcrud.user.exceptions.NoSuchElementFoundException;;

@RestController
@RequestMapping(path = "/spring-rest-api", produces = "application/json")
@CrossOrigin(origins = "*") // Angular Home Page
public class UserController {

	private final UserRepository userRepo;

	@Autowired
	public UserController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@GetMapping("/users")
	public ResponseEntity<Object> getUsers(@RequestParam Number pages,Number size) throws ResourceNotFoundException{
		Pageable page = PageRequest.of(0, 9, Sort.by("name").descending());
		Page<User> pagedResult = userRepo.findAll(page);
		Map<String, Object> responseBody = new LinkedHashMap<>();
		 responseBody.put("users",pagedResult.getContent() );
		 responseBody.put("count",userRepo.count() );
		if (pagedResult.hasContent()) {
			return new ResponseEntity<>(responseBody, HttpStatus.OK);
		 } else {
			throw new ResourceNotFoundException("Users not found in the database");
		}		
	}
	
	@GetMapping("/users/{id}")
/*	
 	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		Optional<User> optUser = userRepo.findById(id);
		if (optUser.isPresent()) {
			return new ResponseEntity<>(optUser.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(optUser.get(), HttpStatus.NOT_FOUND);
	}
*/
 	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) 
 		
		throws ResourceNotFoundException {
			User user = userRepo
				.findById(id)
				.orElseThrow(() -> new NoSuchElementFoundException("User not found for this id :: " + id));
			return ResponseEntity.ok().body(user);
	}

	@PostMapping(path = "/users", consumes = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public User postUser(@Valid @RequestBody User user) {
		return userRepo.save(user);
	}

	@PutMapping(path = "/users/{id}/put", consumes = "application/json")
	public ResponseEntity<User> putUser(@Valid @PathVariable("id") Long id, @RequestBody User user) 
	  throws ResourceNotFoundException{
			Optional<User> optionalUser = userRepo.findById(id);
			if (optionalUser.isPresent()) {
				User existingUser = optionalUser.get();
				existingUser.setName(user.getName());
				existingUser.setEmail(user.getEmail());

				User userUpdated = userRepo.save(existingUser);

				return new ResponseEntity<>(userUpdated, HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("User not found for this id :: " + id);
			}		
	}

	@PatchMapping(path = "/users/{id}/patch", consumes = "application/json")
	public ResponseEntity<User> patchUser(@Valid @PathVariable("id") Long id, @RequestBody @Valid User patch)
			throws ResourceNotFoundException {
			Optional<User> optionalUser = userRepo.findById(id);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();

				if (patch.getName() != null && patch.getName() != "") {
					user.setName(patch.getName());
				}
				if (patch.getEmail() != null && patch.getEmail() != "") {
					user.setEmail(patch.getEmail());
				}

				User updatedUser = userRepo.save(user);
				
				return new ResponseEntity<>(updatedUser, HttpStatus.OK);
			} else {
				throw new ResourceNotFoundException("User not found for this id :: " + id);
			}

		}

	@DeleteMapping(path = "/users/{id}/delete")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
		try {
			userRepo.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
