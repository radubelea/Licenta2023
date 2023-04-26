import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { first } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  }

  constructor(private auth: AuthService) {}

  inSubmission = false

  showAlert = false
  alertMsg = 'Please wait! Your account is being created.'
  alertColour = 'blue'

  login() {
    this.showAlert = true
    this.alertMsg = 'Logging in...'
    this.alertColour = 'blue'
    this.inSubmission = true

   this.auth.login(this.credentials.email, this.credentials.password)
   .subscribe({
      next: () => {
        this.alertMsg = 'Login successful.'
        this.alertColour = 'green'
      },
      error: error => {
        console.log(error)
        this.alertMsg = error.error
        this.alertColour = 'red'
        this.inSubmission = false
        return
      }
   })
  }
}
