import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShareNewsComponent } from './components/share-news/share-news.component';
import { TopTagsComponent } from './components/top-tags/top-tags.component';
import { ListNewsComponent } from './components/list-news/list-news.component';

const routes: Routes = [
  // path for testing to change later
  { path: '', component: ShareNewsComponent},
  { path: 'tags', component: TopTagsComponent },
  { path: 'tag/:tag', component: ListNewsComponent } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
