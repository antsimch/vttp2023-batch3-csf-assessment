import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShareNewsComponent } from './components/share-news/share-news.component';
import { TopTagsComponent } from './components/top-tags/top-tags.component';

const routes: Routes = [
  // path for testing to change later
  { path: '', component: ShareNewsComponent},
  { path: 'tags', component: TopTagsComponent } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
