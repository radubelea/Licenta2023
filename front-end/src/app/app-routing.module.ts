import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { HomeComponent } from './home/home.component';
import { MovieComponent } from './movie/movie.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SearchResultsComponent } from './search-results/search-results.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'about',
    component: AboutComponent
  },
  {
    path: 'movie-details/:id',
    component: MovieComponent
  },
  {
    path: 'search',
    component: SearchResultsComponent,
  },
  {
    path: '**',
    component: NotFoundComponent
  },
  {
    path: 'home',
    redirectTo:''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
