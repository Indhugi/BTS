package com.ibm.bts;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.bts.entity.Bug;
import com.ibm.bts.service.BugService;

@RestController
public class BugController {
	Logger logger=Logger.getLogger(BugController.class.getName());
	@Autowired
	BugService bugService;
	@PostMapping("/bug")
	@ResponseStatus(code = HttpStatus.CREATED)
	String createBug(@RequestBody @Valid Bug bug, BindingResult bindingResult) {
		validateModel(bindingResult);
		logger.setLevel(Level.WARNING);
		return bugService.createBug(bug);
	}
	private void validateModel(Errors bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException("Something went wrong.Please Retry");
		}
	}
	/**
	 * method for searching bug
	 * @param bugId
	 * @return zero or one bug
	 */
	@GetMapping("/bug/{id}")
	Optional<Bug> getBug(@PathVariable("id")String bugId) {
		return bugService.getBug(bugId);
	}
	@GetMapping("/bug")
	List<Bug> getBugs() {
		return bugService.getBugs();
	}

	@PutMapping("/bug/{id}")
	void updateBug(@RequestBody @Valid Bug bug,BindingResult bindingResult,@PathVariable("id") String bugId) {
		validateModel(bindingResult);
		//System.out.println(bugId);	//TODO
		bug.setId(bugId);
		bugService.updateBug(bug);
	}

	@DeleteMapping("/bug/{id}")
	void deleteBug(@PathVariable("id") String bugId) {
		//System.out.println(BugId);	//TODO
		bugService.deleteBug(bugId);
	}
}
