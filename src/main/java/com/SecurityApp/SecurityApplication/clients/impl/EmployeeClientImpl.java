package com.SecurityApp.SecurityApplication.clients.impl;

import com.SecurityApp.SecurityApplication.advice.ApiResponse;
import com.SecurityApp.SecurityApplication.clients.EmployeeClient;
import com.SecurityApp.SecurityApplication.dto.EmployeeDTO;
import com.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class); //MORE LOG = MORE INFO = BETTER

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.trace("trying to retrieve all employees in getAllEmployees");
        try{
            log.trace("Attempting to call the rest client method in getAllEmployees");
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("employees") //appended to base url (already defined)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req,res) -> { //USED TO HANDLE IN REST CLIENTS
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not retrieve all employees");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });

            log.debug("Successfully retreived the employees in getAllEmplopyees");
            log.trace("Retrieved Employees List in getAllEmployees : {}",employeeDTOList.getData());

            return employeeDTOList.getData(); //get data method defined in ApiResponse class
        } catch(Exception e){
            log.error("Exception occurred in getAllEmployees");
            throw new RuntimeException(e); //see tests for method call\
            // A LOT OF METHOD LIKE 500, 4XX errors can arise in rest clients so we sue try catch
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.trace("trying to retrieve employee with id : {} with getEmployeeById", employeeId);
        try{
            ApiResponse<EmployeeDTO> employeeResponse = restClient.get()
                    .uri("employees/{employeeId}" , employeeId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req,res) -> { //USED TO HANDLE IN REST CLIENTS
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not retrieve the employee");
                    })
                    .body(new ParameterizedTypeReference<>() { //receiving data
                    });

            return employeeResponse.getData();
        } catch(Exception e){
            log.error("Exception occurred in getEmployeeById",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.trace("Trying to create Employee : {}", employeeDTO);
        try{
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeDTOApiResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO) //sending data
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req,res) -> { //USED TO HANDLE IN REST CLIENTS
                        log.error("Error 4XX occurred while creating new Employee");
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>(){ //you get the whole entity with status code and body and meta data with this method
                    });
            log.trace("Successfully created a new employee : {}",employeeDTOApiResponse.getBody().getData());
            return employeeDTOApiResponse.getBody().getData();
        } catch(Exception e){
            log.error("Exception occurred while creating Employee",e);
            throw new RuntimeException(e);
        }
    }
}
