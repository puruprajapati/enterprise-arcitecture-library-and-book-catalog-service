package library.service.impl;

import library.config.ApplicationProperties;
import library.dto.OutstandingAmountPerCustomerDTO;
import library.repo.CustomerRepo;
import library.service.CustomerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerScheduleServiceImpl implements CustomerScheduleService {
  @Autowired
  private CustomerRepo customerRepo;

  @Autowired
  private ApplicationProperties applicationProperties;

  @Override
  public void printOutstandingAmountPerCustomer() {
    List<Object[]> tempObject = customerRepo.getOutstandingAmountForAllCustomer(applicationProperties.getFeePerDay());
    List<OutstandingAmountPerCustomerDTO> resultSet = new ArrayList<>();
    for(Object[] result: tempObject){
      OutstandingAmountPerCustomerDTO dataSet = new OutstandingAmountPerCustomerDTO();
      dataSet.setCustomerNumber((String) result[0]);
      dataSet.setName((String) result[1]);
      dataSet.setOutstandingFee((Double) result[2]);
      resultSet.add(dataSet);
    }
    System.out.println("***** Printing Outstanding report at : " + LocalDateTime.now().toString() + "*****");
    System.out.println("-------------------------------------------------------------------------");
    System.out.println("Customer Number   |                  Name               | Outstanding Fee");
    System.out.println("-------------------------------------------------------------------------");
    for(OutstandingAmountPerCustomerDTO data: resultSet){
      System.out.println(data.getCustomerNumber() + "             |            " + data.getName() + "          |    " + String.valueOf(data.getOutstandingFee()));
    }
    System.out.println("-------------------------------------------------------------------------");

  }
}
