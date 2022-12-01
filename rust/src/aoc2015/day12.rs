use std::collections::HashMap;

use crate::DaySolver;

pub struct Day12;

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

fn split_list(input: &str) -> Vec<&str> {
    todo!()
}

fn parse(input: &str) -> Json {
    println!("Parsing {input}");
    let mut chars = input.chars();
    match (chars.next(), chars.next_back()) {
        (Some('{'), Some('}')) => {
            Json::JObject(
                chars.as_str().split(",").map(|attr| {
                    let (name, value) = attr.split_once(":").unwrap();
                    (name[1..name.len()-1].to_string(), parse(value))
                }).collect())
        },
        (Some('['), Some(']')) => {
            let elements: Vec<Json> = chars.as_str().split(",").map(|element| parse(element)).collect();
            Json::JArray(elements)
        },
        (Some('"'), Some('"')) => Json::JString(chars.as_str().to_string()),
        (_, _) => Json::JNumber(input.parse::<i32>().unwrap())
    }
}

impl DaySolver for Day12 {
    fn solve_part1(&self, input: &str) -> i32 {
        flatten_values(&parse(input)).iter().fold(0, |acc, json| match json {
            Json::JNumber(to_add) => acc + to_add,
            _ => acc
        })
    }

    fn solve_part2(&self, input: &str) -> i32 {
        todo!()
    }
}