import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import IUser from 'src/app/models/user.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { first } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private auth: AuthService) {}

  name = new FormControl('', [
    Validators.required,
    Validators.minLength(3)
  ])
  email = new FormControl('', [
    Validators.required,
    Validators.email
  ])

  age = new FormControl<number | null>(null, [
    Validators.required,
    Validators.min(18),
    Validators.max(120)
  ])

  password = new FormControl('', [
    Validators.required,
    Validators.pattern(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/)
  ])

  confirm_password = new FormControl('', [
    Validators.required
  ])

  registerForm = new FormGroup({
    name: this.name,
    email: this.email,
    age: this.age,
    password: this.password,
    confirm_password: this.confirm_password
  })


  showAlert = false
  alertMsg = 'Account is being created...'
  alertColour = 'blue'
  inSubmission = false

  register() {
    this.showAlert = true
    this.alertMsg = 'Account is being created...'
    this.alertColour = 'blue'

    this.auth.createUser(this.registerForm.value as IUser)
    .subscribe({
      next: () => {
        this.alertMsg = 'Success! Your account has been created.'
        this.alertColour = 'green'
      },
      error: error => {
        if(error.status === 200) {
          this.alertMsg = 'Success! Your account has been created.'
          this.alertColour = 'green'
        }
        else {
        console.log(error)
          this.alertMsg = error.error
          this.alertColour = 'red'
          this.inSubmission = false
          return
        }
      }
    })
  }


}
