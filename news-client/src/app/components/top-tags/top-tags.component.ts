import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NewsService } from 'src/app/news.service';

@Component({
  selector: 'app-top-tags',
  templateUrl: './top-tags.component.html',
  styleUrls: ['./top-tags.component.css']
})
export class TopTagsComponent {
  
  timeForm!: FormGroup
  durationOption: number[] = [ 5, 15, 30, 45, 60 ]
  duration!: number

  constructor(
      private fb: FormBuilder,
      private newsSvc: NewsService) {}
  
  ngOnInit(): void {
    this.createForm()
    this.duration = this.timeForm.value['duration']
    this.newsSvc.getNews(this.duration)
  }

  setTime() {
    console.log("in setTime")
    console.log(this.timeForm.value);
    this.duration = parseInt(this.timeForm.value['duration'])
    console.log(this.duration)
    this.newsSvc.getNews(this.duration)
  }

  createForm() {
    this.timeForm = this.fb.group({
      duration: this.fb.control<number>(5, [ Validators.required ])
    })
  }
}
