# dota-draft-helper

## Purpose
Created to help with hero drafting in Dota 2. Idealy, this would get turned into a website with cool graphs and user interfaces, but that is way more work than I have time right now.

## How it works
Scrapes dotabuff.com for every recorded ranked game played. Changes weights in a Bayesian Network based on which heros win with and versus other heros. For example, if X and Y are on the same team and beat Z, the X-Y teammates link and the X-Z and Y-Z opponents link will increase in value. Then, you can run the analysis script to test with this network. In this you can enter each team's hero selections along with a few options and the program will tell you which hero is the best next pick.
