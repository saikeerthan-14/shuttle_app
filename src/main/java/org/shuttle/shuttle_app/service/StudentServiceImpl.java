package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Coordinates;
import org.shuttle.shuttle_app.entity.Passenger;
import org.shuttle.shuttle_app.entity.Status;
import org.shuttle.shuttle_app.entity.Student;
import org.shuttle.shuttle_app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ETACalculator etaCalculator;
    private final PassengerServiceImpl passengerService;
    private final Coordinates collegePlace;
    private CacheService cacheService;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ETACalculator etaCalculator, PassengerServiceImpl passengerService, StopServiceImpl stopService, CacheService cacheService) {
        this.etaCalculator = etaCalculator;
        this.studentRepository = studentRepository;
        this.passengerService = passengerService;
        this.cacheService = cacheService;
        collegePlace = stopService.getStop("CollegePlace").getStopCoordinates();
    }

    @Override
    public String requestPickup(Long suID) throws ServiceUnavailableException {
        // get student based on SUID
        Student student = studentRepository.findBySuID(suID);
        // calculate ETA from collegePlace to shuttle location
        double eta = calculateETA(collegePlace);
        String responseETA = "Shuttle arrives approximately in " + eta + " minutes";

        if (student == null) {
            throw new ServiceUnavailableException("SUID is not in the database");
        }

        // If Student is in IDLE State, move to WAITING state
        if (student.getStudentStatus() == Status.IDLE) {
            student.setStudentStatus(Status.WAITING);
            // Updating student record based on status
            saveToDB(student);
            Passenger passenger = new Passenger(suID, eta, Status.WAITING);
            cacheService.waitList.add(passenger);
            passengerService.saveToDB(passenger);
        } else if(student.getStudentStatus() == Status.WAITING) {
            throw new ServiceUnavailableException("You already requested a ride. " + responseETA);
        } else if (student.getStudentStatus() == Status.PICKED) {
            throw new ServiceUnavailableException("You cannot request a shuttle while travelling in it");
        }

        return responseETA;
    }

    public double calculateETA(Coordinates location) {
        return etaCalculator.calculateETA(location);
    }


    @Override
    public Student addStudentToDB(Student student) throws ServiceUnavailableException {
        if (!isValidSUID(student.getSuID())) {
            throw new ServiceUnavailableException("SUID must be 9 digits");
        }

        if (studentRepository.findBySuID(student.getSuID()) != null) {
            throw new ServiceUnavailableException("SUID already exists");
        }

        try {
            student.setStudentStatus(Status.IDLE);
            saveToDB(student);
            return student;
        } catch (Exception e) {
            throw new ServiceUnavailableException("Error while saving student to database");
        }
    }

    @Override
    public List<Student> generateStudents(int count) throws ServiceUnavailableException {

        List<Student> students = new ArrayList<>();
        // To ensure unique SUIDs
        Set<Long> uniqueSUIDs = new HashSet<>();

        Random random = new Random();

        for (int i = 1; i <= count; i++) {
            Long suID;

            // Generate a unique 9-digit SUID
            do {
                suID = 100000000L + random.nextLong(900000000L);

            } while (uniqueSUIDs.contains(suID));

            uniqueSUIDs.add(suID);

            // Generate a random 10+ character name
            String name = generateRandomString(5 + random.nextInt(5)) + " " + generateRandomString(5 + random.nextInt(5));

            // Generate an 8-character email prefix and set as email
            String suEmail = generateRandomString(8).toLowerCase() + "@syr.edu";

            // Add new Student to the list
            students.add(new Student(suID, name, suEmail));
        }

        return students;
    }

    @Override
    public List<Student> getAllStudents(Status status) throws ServiceUnavailableException {
        return studentRepository.findAllByStudentStatus(status);
    }


    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    public boolean isValidSUID(Long SUID) {
        return SUID != null && SUID.toString().length() == 9 && String.valueOf(SUID).matches("[0-9]+");
    }

    public Student findBySuid(Long suid) {
        return studentRepository.findBySuID(suid);
    }

    public void saveToDB(Student student) {
        studentRepository.save(student);
    }
}
