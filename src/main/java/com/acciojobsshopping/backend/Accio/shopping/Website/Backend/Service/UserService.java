package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.AddUserDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.LogInRequestDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.LogInResponseDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Users;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.AddAdminFirstException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.UserNotFoundException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.WrongPasswordException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;


    @Autowired
    EmailService emailService;

    public Users signUp (AddUserDTO addUserDTO){




        List<Object[]> list=usersRepository.findAdmin();

        int temp=Integer.parseInt(list.get(0)[0].toString());


        String role=addUserDTO.getRole().toString();

        if(temp==0 && role.equals("User")  ){
            throw  new AddAdminFirstException("Add Admin first in database");
        }
        else if(temp==0 && addUserDTO.getPassword().equals("AccioFirstAdmin")==false){
            throw  new AddAdminFirstException("Use first admin password");
        }
        else if (temp==0 && addUserDTO.getPassword().equals("AccioFirstAdmin")==true && role.equals("Admin") ){
            Users user = new Users();
            user.setUserName(addUserDTO.getUserName());
            user.setAddress(addUserDTO.getAddress());
            user.setRole(addUserDTO.getRole().toString());
            user.setPassword(addUserDTO.getPassword());
            user.setEmailAddress(addUserDTO.getEmail());
            user.setPhoneNumber(addUserDTO.getPhoneNumber());

            user.setAdminApprove(true);

            usersRepository.save(user);

            return user;
        }
        else {
            Users user = new Users();
            user.setUserName(addUserDTO.getUserName());
            user.setAddress(addUserDTO.getAddress());
            user.setRole(addUserDTO.getRole().toString());
            user.setPassword(addUserDTO.getPassword());
            user.setEmailAddress(addUserDTO.getEmail());
            user.setPhoneNumber(addUserDTO.getPhoneNumber());

            user.setAdminApprove(false);


            usersRepository.save(user);

            emailService.sendSignInEmail(user);
            return user;
        }
    }

     public LogInResponseDTO logIn(LogInRequestDTO logInRequestDTO){
        String name=logInRequestDTO.getUserName();
        String password=logInRequestDTO.getPassword();
        Users user=usersRepository.findByUserName(name);

        if(user!=null){
            if(password.equals(user.getPassword())==false){
                 throw new WrongPasswordException(" Password incorrect !!");
            }
            return new LogInResponseDTO("success");
        }
        else{
             throw new UserNotFoundException(" user name is wrong !!");
        }

     }

     public  boolean isAdmin(String userName){
        Users user= usersRepository.findByUserName(userName);

        if(user==null){
            throw new UserNotFoundException("user not found");
        }

        return user.isAdminApprove();
     }

     public void approveAdmin(String adminName,int uid){
        if(isAdmin(adminName)==false){
            throw new UserNotFoundException("Admin with name "+adminName+" not in database");
        }

        usersRepository.approveAdmin(uid);
     }

     public Users findById(int uid){
        return usersRepository.findById(uid).orElse(null);
     }
}
