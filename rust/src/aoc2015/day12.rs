use std::borrow::BorrowMut;
use std::collections::HashMap;
use std::iter::Peekable;
use std::str::Chars;

use crate::DaySolver;

pub struct Day12;

#[derive(Debug)]
enum Json {
    JObject(HashMap<String, Json>),
    JArray(Vec<Json>),
    JNumber(i32),
    JString(String)
}

fn flatten_values(json: &Json) -> Vec<&Json> {
    match json {
        Json::JObject(map) => map.values().collect::<Vec<&Json>>().iter().flat_map(|el| flatten_values(el)).collect(),
        Json::JArray(list) => list.iter().flat_map(|el| flatten_values(el)).collect(),
        x => vec![x]
    }
}

fn flatten_values_filter_red(json: &Json) -> Vec<&Json> {
    match json {
        Json::JObject(map) => if map.values().any(|v| match v {
            Json::JString(x) if x == "red" => true,
            _ => false
        }) {
            vec![]
        } else {
            map.values().collect::<Vec<&Json>>().iter().flat_map(|el| flatten_values_filter_red(el)).collect()
        },
        Json::JArray(list) => list.iter().flat_map(|el| flatten_values_filter_red(el)).collect(),
        x => vec![x]
    }
}

fn parse(input: &mut Peekable<Chars>) -> Json {
    match input.peek() {
        Some('{') => {
            let mut map: HashMap<String, Json> = HashMap::new();
            while input.next() != Some('}') {
                let key = input.take_while(|c| *c != ':').filter(|c| *c != '"').fold(String::new(), |x, y| x + &y.to_string());
                let value = parse(input);
                map.insert(key.to_string(), value);
            }
            Json::JObject(map)
        }
        Some('[') => {
            let mut list: Vec<Json> = Vec::new();
            while input.next() != Some(']') {
                list.push(parse(input));
            }
            Json::JArray(list)
        }
        Some('"') => {
            input.next();
            let res = input.take_while(|c| *c != '"').fold(String::new(), |x, y| x + &y.to_string());
            Json::JString(res.to_string())
        }
        Some(c) if c.is_numeric() || *c == '-' => {
            let mut num = String::new();
            // Using next_if might make this prettier
            while input.peek().unwrap().is_numeric() || *input.peek().unwrap() == '-' {
                num.push(input.next().unwrap())
            }
            Json::JNumber(num.parse().unwrap())
        },
        _ => {
            panic!("Could not parse JSON")
        }
    }
}

impl DaySolver for Day12 {
    fn solve_part1(&self, input: &str) -> i32 {
        flatten_values(&parse(input.chars().peekable().borrow_mut())).iter().fold(0, |acc, json| match json {
            Json::JNumber(to_add) => acc + to_add,
            _ => acc
        })
    }

    fn solve_part2(&self, input: &str) -> i32 {
        flatten_values_filter_red(&parse(input.chars().peekable().borrow_mut())).iter().fold(0, |acc, json| match json {
            Json::JNumber(to_add) => acc + to_add,
            _ => acc
        })
    }
}