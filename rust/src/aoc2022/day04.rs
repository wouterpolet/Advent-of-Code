use crate::DaySolver;

pub struct Day04;

impl Day04 {
    fn get_range(&self, text: &str) -> (i32, i32) {
        let (l, r) = text.split_once("-").unwrap();
        (l.parse().unwrap(), r.parse().unwrap())
    }

    fn contains(&self, left: (i32, i32), right: (i32, i32)) -> bool {
        right.0 >= left.0 && right.1 <= left.1
    }

    fn overlapping(&self, left: (i32, i32), right: (i32, i32)) -> bool {
        (right.0 <= left.1 && right.1 >= left.1) ||
            (right.1 >= left.0 && right.0 <= left.1)
    }
}

impl DaySolver for Day04 {
    fn solve_part1(&self, input: &str) -> i32 {
        input.lines().filter(|line| {
            let (left, right) = line.split_once(",").unwrap();
            let range1 = self.get_range(left);
            let range2 = self.get_range(right);
            self.contains(range1, range2) || self.contains(range2, range1)
        }).count() as i32
    }

    fn solve_part2(&self, input: &str) -> i32 {
        input.lines().filter(|line| {
            let (left, right) = line.split_once(",").unwrap();
            let range1 = self.get_range(left);
            let range2 = self.get_range(right);
            self.overlapping(range1, range2) || self.overlapping(range2, range1)
        }).count() as i32
    }
}