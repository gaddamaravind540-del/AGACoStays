package com.agacostays.branch.dto.response;
import java.util.List;
public record BranchPublicResponse(HotelBranchResponse branch,List<HotelBranchPhotoResponse> photos,
 List<ReceptionistContactResponse> receptionists,List<EmergencyContactResponse> emergencyContacts) {}
