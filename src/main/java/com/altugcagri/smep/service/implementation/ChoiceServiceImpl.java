package com.altugcagri.smep.service.implementation;

import com.altugcagri.smep.controller.dto.response.ApiResponse;
import com.altugcagri.smep.persistence.ChoiceRepository;
import com.altugcagri.smep.persistence.model.Choice;
import com.altugcagri.smep.security.UserPrincipal;
import com.altugcagri.smep.service.ChoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChoiceServiceImpl implements ChoiceService {

    private ChoiceRepository choiceRepository;

    public ChoiceServiceImpl(ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteChoiceById(UserPrincipal currentUser, Long choiceId) {
        Choice choice = choiceRepository.findById(choiceId).orElse(null);
        if (choice != null && currentUser.getId().equals(choice.getCreatedBy())) {
            choiceRepository.deleteById(choiceId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Choice deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete choice"));
    }
}
