package lectureydjeon;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import lectureydjeon.external.Payment;
import lectureydjeon.external.PaymentService;

@Entity
@Table(name="Counsel_table")
public class Counsel {

    String testvalue;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long courseId;
    private Long classId;
    private Long fee;
    private String memo;
    private String name;
    private String customerType;
    private String textBook;
    private String counselType;

    @PostPersist
    public void onPostPersist(){
        CounselRegistered counselRegistered = new CounselRegistered();
        BeanUtils.copyProperties(this, counselRegistered);
        counselRegistered.publishAfterCommit();


//        CounselDelete counselDelete = new CounselDelete();
//        BeanUtils.copyProperties(this, counselDelete);
//        counselDelete.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){
    
        System.out.println("PostUpdate START -----------------------------------------");
        if(!this.getCounselType().equals("CLASS_REGISTER")) {
           CounselModiied counselModiied = new CounselModiied();
           BeanUtils.copyProperties(this, counselModiied);
           counselModiied.publishAfterCommit();
           System.out.println ("KAFKA MESSAGE");
        } else {
            System.out.println("NO KAFKA MESSSAGE");

        }
        System.out.println("PostUpdate END -- --------------------------------------");
    }

    @PreUpdate 
    public void onPreUpdate() throws Exception {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        // 여기에서 URL 들어온 값을 확인하여 분기 처리한다. 
        // this 에 속성값으로 

        Payment payment = new Payment();
        // mappings goes here
        payment.setClassId(this.getClassId());
        payment.setCourseId(this.getCourseId());
        payment.setFee(this.getFee());
        payment.setStudent(this.getName());
        payment.setTextBook(this.getTextBook());


        System.out.println("PreUpdate START ----------------------------------------");
             

        if(this.getCounselType().equals("CLASS_REGISTER")) {
            System.out.println ("PAYMENT");
            System.out.println (this.getCounselType());

            if(CounselApplication.applicationContext.getBean(PaymentService.class).pay(payment)) {
                CounselClassRegistered counselClassRegistered = new CounselClassRegistered();
                BeanUtils.copyProperties(this, counselClassRegistered);
                counselClassRegistered.publishAfterCommit();
            } else {
                throw new RollbackException("Failed during payment");
            }
        } else {
            System.out.println ("NO PAYMENT");
            System.out.println (this.getCounselType());
        }

        System.out.println("PreUpdate END  ----------------------------------------");

        /*
        if(CounselApplication.applicationContext.getBean(PaymentService.class).pay(payment)) {
            CounselClassRegistered counselClassRegistered = new CounselClassRegistered();
            BeanUtils.copyProperties(this, counselClassRegistered);
            counselClassRegistered.publishAfterCommit();
        } else {
            throw new RollbackException("Failed during payment");
        }
        */
        // lectureydjeon.external.Class class = new lectureydjeon.external.Class();
        
        // Application.applicationContext.getBean(lectureydjeon.external.ClassService.class)
        //    .registerClass(class);
    }

    @PreRemove
    public void onPreRemove() {
        CounselDelete counselDelete = new CounselDelete();
        BeanUtils.copyProperties(this, counselDelete);
        counselDelete.publishAfterCommit();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getCounselType() {
        return counselType;
    }

    public void setCounselType(String counselType) {
        this.counselType = counselType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    private String getTextBook() {
        return textBook;
    }

    private Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public void setTextBook(String textBook) {
        this.textBook = textBook;
    }
}
