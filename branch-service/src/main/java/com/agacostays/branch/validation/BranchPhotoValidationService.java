package com.agacostays.branch.validation;
import org.springframework.stereotype.Component;
@Component public class BranchPhotoValidationService { public void validateCaption(String caption){ if(caption!=null&&caption.length()>255) throw new IllegalArgumentException("Caption is too long"); } }
