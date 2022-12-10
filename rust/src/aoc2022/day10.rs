use crate::DaySolver;

pub struct Day10;

impl DaySolver for Day10 {
    fn solve_part1(&self, input: &str) -> i32 {
        let mut cycle = 1;
        let mut register = 1;
        let mut count = 0;
        let mut cycles_to_add = vec![20, 60, 100, 140, 180, 220];
        input.lines().for_each(|line| {
            if !cycles_to_add.is_empty() && cycle == cycles_to_add[0] {
                count += cycle* register;
                cycles_to_add.remove(0);
            }
            if line.starts_with("addx") {
                let num: i32 = line.split_once(" ").unwrap().1.parse().unwrap();
                cycle += 1;
                if !cycles_to_add.is_empty() && cycle == cycles_to_add[0] {
                    count += cycle * register;
                    cycles_to_add.remove(0);
                }
                register += num;
            }
            cycle += 1;
        });

        count
    }

    fn solve_part2_str(&self, input: &str) -> String {
        let mut cycle = 1;
        let mut register = 1;
        let mut result = String::from("\n\n");
        let width = 40;
        
        
        input.lines().for_each(|line| {
            let pos: i32 = (cycle - 1) % width;
            result += if pos.abs_diff(register) <= 1 {
                "#"
            } else {
                "."
            };
            if cycle % width == 0 {
                result += "\n";
            }
            if line.starts_with("addx") {
                let num: i32 = line.split_once(" ").unwrap().1.parse().unwrap();
                cycle += 1;
                let pos: i32 = (cycle - 1) % width;
                result += if pos.abs_diff(register) <= 1 {
                    "#"
                } else {
                    "."
                };
                if cycle % width == 0 {
                    result += "\n";
                }
                register += num;
            }
            cycle += 1;
        });

        let readable = result.replace(".", " ").replace("#", "█");
        result + &readable
    }
}