use crate::DaySolver;
use itertools::Itertools;

pub struct Day05;

impl Day05 {
    fn map_to_new_seeds(&self, (start, size): (i64, i64), mapper: &Vec<(i64, i64, i64)>) -> Vec<(i64, i64)> {
        let mut result = Vec::new();

        // Find one in range
        let range = match mapper.into_iter().find(|(_, source, length)| *source <= start && source + length > start) {
            Some(a) => Some(a),
            None => mapper.into_iter().filter(|(_, source, _)| *source > start).min_by_key(|(_, source, _)| source)
        };

        if range.is_none() {
            // No overlapping range, result stays same
            result.push((start, size));
        } else {
            let (dest, origin, length) = range.unwrap();
            // If under range
            if &start < origin {
                if &(start + size) < origin {
                    // Completely under range
                    result.push((start, size));
                } else {
                    // Split into out range and in range
                    result.push((start, origin - start));
                    result.append(&mut self.map_to_new_seeds((*origin, start + size - origin), mapper))
                }
            }
            // If completely in range
            else if origin + length >= start + size {
                result.push((dest + start - origin, size))
            } else {
                // Split range
                result.push((dest + start - origin, origin + length - start));
                result.append(&mut self.map_to_new_seeds((origin + length, start + size - origin - length), mapper))
            }
        }

        result
    }
}

impl DaySolver for Day05 {
    fn solve_part1_i64(&self, input: &str) -> i64 {
        let mut groups = input.split("\n\n");

        let mut seeds: Vec<i64> = groups.next()
            .and_then(|x| x.split_once(": "))
            .unwrap()
            .1
            .split_ascii_whitespace()
            .map(|c| c.parse::<i64>().unwrap())
            .collect();

        for mapper in groups {
            let mut lines = mapper.lines();
            lines.next();

            let ranges: &Vec<(i64, i64, i64)> = &lines.map(|l| {
                let numbers: Vec<i64> = l.split_ascii_whitespace().map(|x| x.parse::<i64>().unwrap()).collect();
                (numbers[0], numbers[1], numbers[2])
            }).collect();

            for i in 0..seeds.len() {
                seeds[i] = match ranges.into_iter().find(|(_, source, length)| *source <= seeds[i] && source + length > seeds[i]) {
                    Some((dest, source, _)) => dest + seeds[i] - source,
                    None => seeds[i]
                };
            }
        }

        seeds.into_iter().min().unwrap()
    }

    fn solve_part2_i64(&self, input: &str) -> i64 {
        let mut groups = input.split("\n\n");

        let mut seeds: Vec<(i64, i64)> = groups.next()
            .and_then(|x| x.split_once(": "))
            .unwrap()
            .1
            .split_ascii_whitespace()
            .chunks(2)
            .into_iter()
            .map(|mut c| (c.next().unwrap().parse::<i64>().unwrap(), c.next().unwrap().parse::<i64>().unwrap()))
            .collect();

        for mapper in groups {
            let mut lines = mapper.lines();
            lines.next();

            let ranges: &Vec<(i64, i64, i64)> = &lines.map(|l| {
                let numbers: Vec<i64> = l.split_ascii_whitespace().map(|x| x.parse::<i64>().unwrap()).collect();
                (numbers[0], numbers[1], numbers[2])
            }).collect();

            let mut new_seeds = Vec::<(i64, i64)>::new();
            for seed_range in seeds {
                new_seeds.append(&mut self.map_to_new_seeds(seed_range, ranges));
            }
            seeds = new_seeds;
        }

        seeds.into_iter().min().unwrap().0
    }
}
