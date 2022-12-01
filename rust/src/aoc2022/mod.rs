use crate::{DaySolver, YearSolver};

mod day01;

pub struct Year2022;

impl YearSolver for Year2022 {
    fn get_day_solver(&self, day: &i32) -> Option<&dyn DaySolver> {
        match day {
            1 => Some(&day01::Day01),
            _ => None
        }
    }
}
