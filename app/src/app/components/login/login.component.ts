import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthProvider } from 'src/app/providers/auth.providers';
import { CustomAlertComponent } from '../custom-alert/custom-alert.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  @ViewChild('alert') alert: CustomAlertComponent = new CustomAlertComponent();
  loginForm: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authProvider: AuthProvider,

  ) {

    this.loginForm = this.fb.group({
      USER_NAME: [null, Validators.required],
      OTP: [null],
      PASSWORD: [null, Validators.compose([Validators.required, Validators.minLength(4)])]
    });
  }

  onSubmit(): void {
    this.alert.hide();
    if (this.loginForm.valid) {
      const email = this.loginForm.value.USER_NAME;
      const password = this.loginForm.value.PASSWORD;
      const code = this.loginForm.value.OTP;

      this.authProvider.authenticate(email, password, code)
        .subscribe(data => {
          if (data.success) {
            this.loginForm.reset();
           
           this.alert.showSuccess("Successfully Login")
            
          } else {
            this.alert.show(data.error);
          }
        }, err => {
          console.error('error', err);
          this.alert.showError('Network error, Please try again later.');
        });
    }
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    
  }

}
