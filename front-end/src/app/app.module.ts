import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { NavComponent } from './nav/nav.component';
import { SharedModule } from './shared/shared.module';
import { UserModule } from './user/user.module';
import { ReviewModule } from './review/review.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HttpClientModule } from '@angular/common/http';
import { NotFoundComponent } from './not-found/not-found.component';
import { AboutComponent } from './about/about.component';
import { MoviesListComponent } from './movies-list/movies-list.component';
import { MovieComponent } from './movie/movie.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { FilterComponent } from './filter/filter.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { TruncatePipe } from './pipes/truncate.pipe';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavComponent,
    MoviesListComponent,
    NotFoundComponent,
    AboutComponent,
    MovieComponent,
    FilterComponent,
    SearchBarComponent,
    SearchResultsComponent,
    TruncatePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UserModule,
    SharedModule,
    ReviewModule,
    BrowserAnimationsModule,
    MatGridListModule,
    MatCardModule,
    HttpClientModule,
    MatSnackBarModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
