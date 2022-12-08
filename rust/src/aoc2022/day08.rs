use crate::DaySolver;

pub struct Day08;

fn check_tree(trees: &Vec<Vec<i32>>, row: usize, col: usize) -> bool {
    (0..row).all(|r| trees[r][col] < trees[row][col])
    || (row+1..trees.len()).all(|r| trees[r][col] < trees[row][col])
    || (0..col).all(|c| trees[row][c] < trees[row][col])
    || (col+1..trees[0].len()).all(|c| trees[row][c] < trees[row][col])
}

fn scenic_score(trees: &Vec<Vec<i32>>, row: usize, col: usize) -> i32 {
    let a = (0..row).rev().fold((0, true), |(acc, done), r| {
        if done {
            (acc + 1, trees[r][col] < trees[row][col])
        } else {
            (acc, done)
        }
    }).0;
    let b = (row+1..trees.len()).fold((0, true), |(acc, done), r| {
        if done {
            (acc + 1, trees[r][col] < trees[row][col])
        } else {
            (acc, done)
        }
    }).0;
    let c = (0..col).rev().fold((0, true), |(acc, done), c| {
        if done {
            (acc + 1, trees[row][c] < trees[row][col])
        } else {
            (acc, done)
        }
    }).0;
    let d = (col+1..trees[0].len()).fold((0, true), |(acc, done), c| {
        if done {
            (acc + 1, trees[row][c] < trees[row][col])
        } else {
            (acc, done)
        }
    }).0;
    a * b * c * d
}

impl DaySolver for Day08 {
    fn solve_part1(&self, input: &str) -> i32 {
        let trees: Vec<Vec<i32>> = input.lines().map(|l| l.chars().map(|t| t.to_string().parse().unwrap()).collect()).collect();
        let mut count = (trees.len() * 2 + trees[0].len() * 2 - 4) as i32;

        let rows = trees.len();
        let cols = trees[0].len();

        for row in 1..rows-1 {
            for col in 1..cols-1 {
                if check_tree(&trees, row, col) {
                    count += 1;
                }
            }
        }
        
        count
    }

    fn solve_part2(&self, input: &str) -> i32 {
        let trees: Vec<Vec<i32>> = input.lines().map(|l| l.chars().map(|t| t.to_string().parse().unwrap()).collect()).collect();
        let rows = trees.len();
        let cols = trees[0].len();
        (1..rows-1).flat_map(|row| (1..cols-1).map(|col| scenic_score(&trees, row, col)).collect::<Vec<i32>>()).max().unwrap()
    }
}
