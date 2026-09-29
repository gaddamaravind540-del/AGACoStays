package com.agacostays.branch.dto.response;
import java.util.List;
public record BranchContactResponse(List<ReceptionistContactResponse> receptionists,List<EmergencyContactResponse> emergencyContacts) {}
