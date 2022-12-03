
use crate::DaySolver;

pub struct Day03;

impl Day03 {
    fn char_to_score(&self, c: char) -> i32 {
        if c.is_uppercase() {
            (c as u32 - 'A' as u32 + 27) as i32
        } else {
            (c as u32 - 'a' as u32 + 1) as i32
        }
    }
}

impl DaySolver for Day03 {


    fn solve_part1(&self, input: &str) -> i32 {
        input.lines().map(|items| {
            let (left, right) = items.split_at(items.len() / 2);
            let shared = left.chars().find(|item|
                right.contains(&item.to_string())
            ).unwrap();
            self.char_to_score(shared)
        }).sum()
    }

    fn solve_part2(&self, input: &str) -> i32 {
        input.lines().collect::<Vec<&str>>().chunks(3).map(|group| {
            group[0].chars().find_map(|c|
                if group[1..].iter().all(|b| b.contains(&c.to_string())) {
                    Some(self.char_to_score(c))
                } else {
                    None
                }).unwrap()
        }).sum()
    }
}