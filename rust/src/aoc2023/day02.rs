use crate::DaySolver;
use regex::Regex;

pub struct Day02;

#[derive(Debug)]
struct Game {
    id: u8,
    draws: Vec<Draw>
}

#[derive(Debug)]
struct Draw {
    red: u8,
    green: u8,
    blue: u8
}

impl Day02 {
    fn parse(&self, input: &str) -> Vec<Game> {
        input.lines().map(|l| {
            let regex = Regex::new("Game (?<id>\\d+): (?<game>.+)").unwrap();
            let res = regex.captures(l).unwrap();
            let id = res["id"].parse::<u8>().unwrap();
            let draws = res["game"].split("; ").map(|draw_text| {
                Draw {
                    red: draw_text.split(", ").find(|x| x.contains("red")).and_then(|x| x.split_once(" ").unwrap().0.parse::<u8>().ok()).unwrap_or(0),
                    green: draw_text.split(", ").find(|x| x.contains("green")).and_then(|x| x.split_once(" ").unwrap().0.parse::<u8>().ok()).unwrap_or(0),
                    blue: draw_text.split(", ").find(|x| x.contains("blue")).and_then(|x| x.split_once(" ").unwrap().0.parse::<u8>().ok()).unwrap_or(0)
                }
            }).collect();
            
            Game {
                id,
                draws
            }
        }).collect()
    }
}

impl DaySolver for Day02 {
    fn solve_part1(&self, input: &str) -> i32 {
        let max_red = 12;
        let max_green = 13;
        let max_blue = 14;

        self.parse(input)
            .into_iter()
            .filter(|g| (&g.draws).into_iter().all(|d| d.red <= max_red && d.green <= max_green && d.blue <= max_blue))
            .map(|g| g.id as i32)
            .sum()
    }

    fn solve_part2(&self, input: &str) -> i32 {
        self.parse(input)
            .into_iter()
            .map(|g| 
                (&g.draws).into_iter().map(|d| d.red).max().unwrap() as i32 *
                (&g.draws).into_iter().map(|d| d.green).max().unwrap() as i32 *
                (&g.draws).into_iter().map(|d| d.blue).max().unwrap() as i32)
            .sum()
    }
}

