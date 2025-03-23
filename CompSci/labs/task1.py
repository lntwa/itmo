def yaml_to_xml(yaml_file, xml_file):
    with open(yaml_file, 'r', encoding='utf-8') as f:
        lines = f.readlines()
        lines = [line for line in lines if not line.strip().startswith('classes:')]

    xml_content = ['<schedule>\n']
    current_day = False
    inside_class = False

    for line in lines[1:]:
        line = line.strip()

        if line.startswith('- day:'):
            if current_day:
                xml_content.append('    </classes>\n')
                xml_content.append('  </day>\n')
            current_day = True
            inside_class = False

        elif line.startswith('name:'):
            day_name = line.split(':', 1)[1].strip()
            xml_content.append(f'  <day name="{day_name}">\n')
            xml_content.append('    <classes>\n')

        elif line.startswith('- class:'):
            if inside_class:
                xml_content.append('      </class>\n')
            xml_content.append('      <class>\n')
            inside_class = True

        elif ':' in line:
            key, value = line.split(':', 1)
            key = key.strip()
            value = value.strip()
            xml_content.append(f'        <{key}>{value}</{key}>\n')

    if current_day:
        if inside_class:
            xml_content.append('      </class>\n')
        xml_content.append('    </classes>\n')
        xml_content.append('  </day>\n')

    xml_content.append('</schedule>')

    with open(xml_file, 'w', encoding='utf-8') as f:
        f.writelines(xml_content)

yaml_to_xml('schedule.yaml', 'task1_rez.xml')

