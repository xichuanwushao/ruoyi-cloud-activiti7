/*
 * @Author: sdf iamsdf@163.com
 * @Date: 2022-10-05 20:58:00
 * @LastEditors: sdf iamsdf@163.com
 * @LastEditTime: 2022-10-05 20:59:24
 * @FilePath: \ryvue3\src\js\customTranslate\customTranslate.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import translations from './translationsGerman';
export default function customTranslate(template, replacements) {
  replacements = replacements || {};
  template = translations[template] || template;
  return template.replace(/{([^}]+)}/g, function (_, key) {
    var str = replacements[key];
    if (translations[replacements[key]] != null && translations[replacements[key]] != 'undefined') {
      str = translations[replacements[key]];
    }
    return str || '{' + key + '}';
  });
}