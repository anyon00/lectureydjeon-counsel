package lectureydjeon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Optional;

 @RestController
 public class CounselController {
    @Autowired 
    CounselRepository counselRepository;

    @PostMapping(value = "/counsel")
    public Counsel registerCounsel(@RequestBody Map<String, String> param) {

        Counsel cus = new Counsel();

        cus.setCourseId(Long.parseLong(param.get("courseId")));
        cus.setClassId(Long.parseLong(param.get("calssId")));
        cus.setFee(Long.parseLong(param.get("fee")));
        cus.setName(param.get("name"));
        cus.setMemo(param.get("memo"));
        cus.setTextBook(param.get("textBook"));
        cus.setCustomerType(param.get("customerType"));
//        cus.setCounselType("REGISTER");
        cus.setCounselType(param.get("counselType"));
 
        cus = counselRepository.save(cus);
        System.out.println(cus);
 
        return cus;
    }
 
    @PatchMapping(value = "/counsel/{id}")
     public Counsel modifyCounsel(@RequestBody Map<String, String> param, @PathVariable String id) {
 
        Optional<Counsel> opt = counselRepository.findById(Long.parseLong(id));
        Counsel cus = null;
 
        if (opt.isPresent()) {
            cus = opt.get();
 
            if (param.get("courseId") != null)
                cus.setCourseId(Long.parseLong(param.get("courseId")));
            if (param.get("fee") != null)
                cus.setFee(Long.parseLong(param.get("fee")));
            if (param.get("name") != null)
                cus.setName(param.get("name"));
            if (param.get("classID") != null) 
                cus.setClassId(Long.parseLong(param.get("classId")));
            if (param.get("textBook") != null)
                cus.setTextBook(param.get("textBook"));
            if (param.get("memo") != null)
                cus.setMemo(param.get("memo"));
            if (param.get("customerType") != null)
                cus.setCustomerType(param.get("customerType"));
            //if (param.get("counselType") != null)
            //    cus.setCounselType(param.get("counselType"));
            cus.setCounselType("TEST");
            cus = counselRepository.save(cus);
        }
 
        return cus;
    }
 
     @PutMapping(value = "/counsel/{id}")
     public Counsel modifyCounselPut(@RequestBody Map<String, String> param, @PathVariable String id) {
         return this.modifyCounsel(param, id);
     }
 
     @GetMapping(value = "/counsel/{id}")
     public Counsel inquiryClassById(@PathVariable String id) {
 
         Optional<Counsel> opt = counselRepository.findById(Long.parseLong(id));
         Counsel cus = null;
 
         if (opt.isPresent())
             cus = opt.get();
 
         return cus;
     }
 
     @GetMapping(value = "/counsel")
     public Iterable<Counsel> inquiryClass() {
 
         Iterable<Counsel> iter = counselRepository.findAll();
 
         return iter;
     }
 
     @DeleteMapping(value = "/counsel/{id}")
     public boolean deleteClass(@PathVariable String id) {
         boolean result = false;
 
         counselRepository.deleteById(Long.parseLong(id));
         result = true;
 
         return result;
     }


 }
