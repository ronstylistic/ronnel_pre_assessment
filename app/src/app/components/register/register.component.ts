import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthProvider } from 'src/app/providers/auth.providers';
import { CustomAlertComponent } from '../custom-alert/custom-alert.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  @ViewChild('alert') alert: CustomAlertComponent = new CustomAlertComponent();
  loginForm: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authProvider: AuthProvider
  ) {

    this.loginForm = this.fb.group({
      EMAIL: [null, Validators.compose([Validators.required, Validators.pattern('[^ @]*@[^ @]*')])],
      FIRST_NAME: [null, Validators.required],
      LAST_NAME: [null, Validators.required],
      MIDDLE_NAME: [null],
      PASSWORD: [null, Validators.compose([Validators.required, Validators.minLength(6)])]
    });
  }

  onSubmit(): void {
    this.alert.hide();
    if (this.loginForm.valid) {

      const user: User = new User();
      const password = this.loginForm.value.PASSWORD;
      user.email = this.loginForm.value.EMAIL;
      user.lastName = this.loginForm.value.LAST_NAME;
      user.firstName = this.loginForm.value.FIRST_NAME;
      user.middleName = this.loginForm.value.MIDDLE_NAME;
   
      this.authProvider.register(user, password)
        .subscribe(data => {
          if (data.success) {
            this.loginForm.reset();
            this.alert.showSuccess('You successfully registered.');

            setTimeout(() => {
              this.router.navigate(['/login']);
            }, 3000);

          } else {
            this.alert.showError(data.error.txt);
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
