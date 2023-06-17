package tn.corilus.authentification.authentification.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.corilus.authentification.authentification.entities.*;
import tn.corilus.authentification.authentification.repository.RoleRepository;
import tn.corilus.authentification.authentification.repository.UserRepository;
import tn.corilus.authentification.authentification.security.jwt.JwtUtils;
import tn.corilus.authentification.authentification.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;




@RestController
@RequestMapping("/authentication")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;
  private final DoctorService doctorService;
  private final AdminService adminService;
  private final AuthService authService;
  private final SupportService supportService;

  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils, DoctorService doctorService, AdminService adminService, AuthService authService, SupportService supportService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.encoder = encoder;
    this.jwtUtils = jwtUtils;
    this.doctorService = doctorService;
    this.adminService = adminService;
    this.authService = authService;
    this.supportService = supportService;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    if (!userRepository.existsByUsername(loginRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Nom d'utilisateur inexistant!");
    }

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


    String jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername(),userDetails.getAuthorities());

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    if(doctorService.getDoctor(userDetails.getId()) != null) {
      if (!doctorService.getDoctor(userDetails.getId()).isApproved()) {
        return ResponseEntity.badRequest().body("Votre Compte N'a Pas été Approver !");
      }
    if (doctorService.getDoctor(userDetails.getId()).isBlocked()) {
        return ResponseEntity.badRequest().body("Votre Compte Est Bloquer !");
      }
    }

      return ResponseEntity.ok()
              .body(new LoginResponse(userDetails.getId(),
                      userDetails.getUsername(),
                      userDetails.getEmail(),
                      userDetails.getName(),
                      userDetails.getLastname(),
                      userDetails.getPhone(),
                      userDetails.getGender(),
                      userDetails.getPostedAt(),
                      userDetails.getLastUpdatedAt(),
                      jwt,
                      roles));

  }

  @PostMapping("/admin/signup")
  public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminSignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }

    // Create new admin's account
    Admin utilisateur = new Admin();
    utilisateur.setUsername( signUpRequest.getUsername());
    utilisateur.setEmail( signUpRequest.getEmail());
    utilisateur.setPassword(encoder.encode(signUpRequest.getPassword()));
    utilisateur.setPhone( signUpRequest.getPhone());
    utilisateur.setGender( signUpRequest.getGender());
    utilisateur.setLastname( signUpRequest.getLastname());
    utilisateur.setName( signUpRequest.getName());
    utilisateur.setHirarchie( signUpRequest.getHirarchie());
    utilisateur.setAddress( signUpRequest.getAddress());

    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role utilisateurRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(utilisateurRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "ROLE_ADMIN" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "ROLE_SUPPORT" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_SUPPORT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "ROLE_DOCTOR" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
        }
      });
    }

    utilisateur.setRoles(roles);


    return ResponseEntity.ok().body(userRepository.save(utilisateur));
  }


  @PostMapping("/doctor/signup")
  public ResponseEntity<?> registerDoctor(@Valid @RequestBody DoctorSignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Le nom d'utilisateur est déjà pris !");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body("L'adresse e-mail est déjà utilisée !");
    }

    // Create new doctor's account
    Doctor utilisateur = new Doctor();
    utilisateur.setUsername( signUpRequest.getUsername());
    utilisateur.setEmail( signUpRequest.getEmail());
    utilisateur.setPassword( encoder.encode(signUpRequest.getPassword()));
    utilisateur.setPhone( signUpRequest.getPhone());
    utilisateur.setGender( signUpRequest.getGender());
    utilisateur.setLastname( signUpRequest.getLastname());
    utilisateur.setName( signUpRequest.getName());
    utilisateur.setNiss( signUpRequest.getName());
    utilisateur.setSpeciality( signUpRequest.getSpeciality());
    utilisateur.setAddress( signUpRequest.getAddress());

    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role utilisateurRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(utilisateurRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "ROLE_ADMIN" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "ROLE_SUPPORT" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_SUPPORT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "ROLE_DOCTOR" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
        }
      });
    }

    utilisateur.setRoles(roles);


    return ResponseEntity.ok().body(userRepository.save(utilisateur));
  }


@PostMapping("/support/signup/{idAdmin}")
  public ResponseEntity<?> registerSupport(@Valid @RequestBody SupportSignUpRequest signUpRequest,@PathVariable("idAdmin") Long idAdmin) {
    String response= authService.usernameOrMailAlreadyExists(signUpRequest);
  System.out.println(response);
    if(!response.contains("Valid")){
     return ResponseEntity.badRequest().body(response);
    }

    return ResponseEntity.ok(authService.registerSupport(signUpRequest,idAdmin));
  }



  @PostMapping("/signout")
  public ResponseEntity<?> logoutUtilisateur() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("You've been signed out!");
  }


  @PostMapping("/doctor/picture/{idUser}")
  public ResponseEntity<?> registerUser(@PathVariable("idUser") Long idUser,@RequestParam("file") MultipartFile file) throws IOException {

    return ResponseEntity.ok(doctorService.addPicture(idUser,file));
  }
  @PostMapping("/admin/picture/{idUser}")
  public ResponseEntity<?> registerAdmin(@PathVariable("idUser") Long idUser,@RequestParam("file") MultipartFile file) throws IOException {

    return ResponseEntity.ok(adminService.addPicture(idUser,file));
  }
  @PostMapping("/support/picture/{idUser}")
  public ResponseEntity<?> addSupportPicture(@PathVariable("idUser") Long idUser,@RequestParam("file") MultipartFile file) throws IOException {

    return ResponseEntity.ok(supportService.addPicture(idUser,file));
  }

}
