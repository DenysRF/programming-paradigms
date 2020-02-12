use std::thread;

fn main() {
   let mut a = [6;100]; // initialise a to array with 100 times 6

   let t1 = thread::spawn(move || {
      for i in 0 ..50 {
         a[i] = 0;
      }
   });

   let t2 = thread::spawn(move || {
      for i in 50 .. 100 {
         a[i] = 0;
      }
   });

   t1.join().unwrap();
   t2.join().unwrap();

   for i in 0 .. 100 {
       println!("{}", a[i]);
   }
}

// 6.3.3
//use std::thread;
//use std::sync::{Mutex, Arc};
//
//fn main() {
//   let a = Arc::new(Mutex::new(vec![6;100])); // initialise a to array with 100 times 6
//
//   let a1 = a.clone();
//
//   let t1 = thread::spawn(move || {
//      for i in 0 .. 50 {
//        let mut a = a1.lock().unwrap();
//        a[i] = 0;
//      }
//   });
//
//   let a2 = a.clone();
//   let t2 = thread::spawn(move || {
//      for i in 50 .. 100 {
//        let mut a = a2.lock().unwrap();
//        a[i] = 0;
//      }
//   });
//
//   t1.join().unwrap();
//   t2.join().unwrap();
//
//   let a = a.lock().unwrap();
//
//   for i in 0 .. 100 {
//       println!("{}", a[i]);
//   }
//}