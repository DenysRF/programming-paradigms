use std::thread;

fn main() {
   let a = [6;100]; // initialise a to array with 100 times 6

   let mut sum1 = 0;
   let mut sum2 = 0;
   let t1 = thread::spawn(move || {
      for i in 0 ..50 {
         sum1 = sum1 + a[i];
      }
      sum1
   });

   let t2 = thread::spawn(move || {
      for i in 50 .. 100 {
         sum2 = sum2 + a[i];
      }
      sum2
   });
   sum1 = t1.join().unwrap();
   sum2 = t2.join().unwrap();
   println!("{}",sum1 + sum2);
}

// 6.3.4
//use std::thread;
//use std::sync::{Mutex, Arc};
//
//fn main() {
//   let a = [6;100]; // initialise a to array with 100 times 6
//
//   let sum = Arc::new(Mutex::new(0));
//
//   let s1 = sum.clone();
//   let t1 = thread::spawn(move || {
//      for i in 0 ..50 {
//         *s1.lock().unwrap() += a[i];
//      }
//
//   });
//
//   let s2 = sum.clone();
//   let t2 = thread::spawn(move || {
//      for i in 50 .. 100 {
//         *s2.lock().unwrap() += a[i];
//      }
//
//   });
//
//   t1.join().unwrap();
//   t2.join().unwrap();
//
//   println!("{}",sum.lock().unwrap());
//}