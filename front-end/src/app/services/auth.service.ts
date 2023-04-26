import { Injectable } from '@angular/core';
import { Observable, of, throwError, BehaviorSubject } from 'rxjs';
import IUser from '../models/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, delay, filter, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ActivatedRoute, NavigationEnd } from '@angular/router';
import { environment } from 'src/environments/environment';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private redirect = false
  private userSubject: BehaviorSubject<IUser>
  public user: Observable<IUser>
  public isAuthenticated$: Observable<boolean>
  public isAuthenticatedWithDelay$: Observable<boolean>

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Accept': '*/*'
    }),
  }

  constructor(private http: HttpClient,
              private router: Router,
              private route: ActivatedRoute
  ){
    this.userSubject = new BehaviorSubject<IUser>(JSON.parse(localStorage.getItem('user') || '{}'));
    this.user = this.userSubject.asObservable();

    this. isAuthenticated$ = this.user.pipe(
      map(user => JSON.stringify(user) != '{}' && !!user)
    )
    this.isAuthenticatedWithDelay$ = this.isAuthenticated$.pipe(
      delay(1000)
    )
/*
    this.router.events.pipe(
      filter(e => e instanceof NavigationEnd),
      map(e => this.route.firstChild),
      switchMap(route => route?.data ?? of({}))
    ).subscribe(data => {
      this.redirect = data.authOnly ?? false
    })*/
  }

  public login(email: string, password: string) {
    console.log(JSON.stringify({email, password}))
    return this.http.post<IUser>(environment.backend_url + '/authenticate', JSON.stringify({ email, password }), this.httpOptions)
        .pipe(map(user => {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('user', JSON.stringify(user));
            this.userSubject.next(user);
            this.router.navigateByUrl('/')
            console.log(JSON.stringify(user))
            return user;
        }));
  }

  public createUser(userData: IUser) {
    if(!userData.password) {
      throw new Error("Password not provided!")
    }
    return this.http.post(environment.backend_url + '/registration', JSON.stringify(userData), this.httpOptions)
  }

  public logout($event?: Event) {
    if($event){
      $event.preventDefault()
    }

    localStorage.removeItem('user');
    this.userSubject.next(null!);

    if(this.redirect) {
      this.router.navigateByUrl('/')
    }
  }

  getDecodedAccessToken(): any {
    const user = localStorage.getItem('user')
    if (!user){
      return null;
    }
    try {
      return jwt_decode(user);
    } catch(Error) {
      return null;
    }
  }
}
