void Likes(List<string> userNames)
{
    if (userNames.Count == 0)
    {
        Console.WriteLine("no one likes this");
    }

    if (userNames.Count == 1)
    {
        Console.WriteLine($"{userNames[0]} likes this");
    }

    if (userNames.Count == 2)
    {
        Console.WriteLine($"{userNames[0]} and {userNames[1]} like this");
    }

    if (userNames.Count == 3)
    {
        Console.WriteLine($"{userNames[0]}, {userNames[1]} and {userNames[2]} like this");
    }

    if (userNames.Count > 3)
    {
        var firstTwo = userNames.Take(2).ToArray();
        Console.WriteLine($"{firstTwo[0]}, {firstTwo[1]} and {userNames.Count - 2} others like this");
    }
}