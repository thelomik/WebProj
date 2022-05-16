package pl.pk.contrloler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pk.user.User;
import pl.pk.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AppControler {
    @Autowired
    private UserRepository userRepository;


    String resetTokenString;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }


    @GetMapping("/register")
    public String showSingUpForm(Model model){
        model.addAttribute("user",new User());
               return "register_form";
    }

    @PostMapping("/process_register")
    public String processRegistration( User user){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("login")
    public String showLoginPage(){
            return "login";

    }


}
